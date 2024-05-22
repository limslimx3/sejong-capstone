package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.*;
import com.sejong.capstone.domain.*;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.VideoLikeRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VideoService {

    @Value("${file.dir}")
    private String fileDir;

    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;
    private final VideoLikeRepository videoLikeRepository;

    private final WebClient webClientForFastAPI;

    @Transactional
    public Long saveVideo(Long memberId, VideoForm videoForm) throws IOException {
        Member member = memberRepository.findById(memberId).orElseThrow();
        Long videoId = saveInDb(member, videoForm);
        return videoId;
    }

    /**
     * FastAPI측으로 비디오 Id값 전달후 최종 결과값 담긴 JSON 파일 반환받음
     *  - 비동기 처리 위해 CompletableFuture 사용
     */
    public CompletableFuture<TotalJsonResult> communicateWithFastAPIAsync(Long videoId, VideoForm videoForm) throws IOException {
        saveInStorage(videoId, videoForm.getVideo());
        return webClientForFastAPI.get()
                .uri("/api/json/" + videoId)
                .retrieve()
                .bodyToMono(TotalJsonResult.class)
                .toFuture();
    }

    /**
     * 인공지능 모델 서빙 결과값 담긴 JSON 반환받아 파싱후 DB에 저장
     */
    @Transactional
    public void jsonParsing(Long videoId, TotalJsonResult totalJsonResult) {
        Video video = videoRepository.findById(videoId).orElseThrow();
        for (SubtitleJsonResult subtitleJsonResult : totalJsonResult.getSubtitleList()) {
            int idx = 0;
            SubtitleSentence subtitleSentence = SubtitleSentence.createSubtitleSentence(1, subtitleJsonResult.getStart(), subtitleJsonResult.getKorText(), subtitleJsonResult.getEngText(), video);
            for (String korWord : subtitleJsonResult.getKorWordText()) {
                SubtitleWord.createSubtitleWord(++idx, korWord, subtitleSentence);
            }
        }
    }

    /**
     * DB에서 Video,Member,VideoTag,SubtitleSentence,SubtitleWord,Post 패치조인으로 한번에 조회한후 화면에 렌더링하기 위한 VideoInfoResponse 형태로 반환
     */
    public VideoResponse getVideoInfos(Long videoId, Long memberId) {
        Video video = videoRepository.findByIdUsingFetchJoin(videoId);
        return new VideoResponse(video, memberId);
    }

    /**
     * 비디오 좋아요 처리
     */
    @Transactional
    public int setLikeVideo(Member loginMember, Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow();
        VideoLike before = video.getVideoLikes().stream()
                .filter(videoLike -> videoLike.getMember().getId().equals(loginMember.getId()))
                .findFirst()
                .orElse(null);
        if(before == null) { //사용자가 해당 비디오에 대해 좋아요를 누른적이 없는 경우
            video.likeVideo();
            VideoLike.createVideoLike(video, loginMember);
        } else {    //사용자가 해당 비디오에 대해 좋아요를 누른적이 있는 경우
            video.dislikeVideo();
            videoLikeRepository.delete(before);
        }
        return video.getLike();
    }

    /**
     * 비디오 조회수 증가 처리
     */
    @Transactional
    public void addViews(Long videoId) {
        Video video = videoRepository.findById(videoId).orElseThrow();
        video.addViews();
    }

//    /**
//     * 특정 경로의 비디오에 접근하여 Resource 형태로 반환
//     */
//    public Resource getVideoFromStorage(String videoPath) throws MalformedURLException {
//        Path path = Paths.get(videoPath);
//        return new UrlResource(path.toUri());
//    }

    //스토리지에 MultipartFile 업로드
    private void saveInStorage(Long videoId, MultipartFile multipartFile) throws IOException {
        String ext = extractExt(multipartFile);
        String storageFileName = videoId + "." + ext;
//        String storageFileName = createStorageFileName(ext);
        multipartFile.transferTo(new File(fileDir + storageFileName));
    }

    //DB에 비디오 관련 정보 저장(SubtitleSentence는 아직 저장X)
    private Long saveInDb(Member member, VideoForm videoForm) {
        Video video = Video.createVideo(member, videoForm.getTitle(), videoForm.getContent(), videoForm.getVideoTags());
        Video savedVideo = videoRepository.save(video);
        return savedVideo.getId();
    }

//    private String createStorageFileName(String ext) {
//        String uuid = UUID.randomUUID().toString();
//        return uuid + "." + ext;
//    }

    //확장자명 추출
    private String extractExt(MultipartFile multipartFile) {
        String originalFilename = multipartFile.getOriginalFilename();
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
