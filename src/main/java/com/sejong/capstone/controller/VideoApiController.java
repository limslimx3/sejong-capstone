package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.RecommendVideoResponse;
import com.sejong.capstone.controller.dto.VideoForm;
import com.sejong.capstone.controller.dto.VideoResponse;
import com.sejong.capstone.controller.dto.VideoTagResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.domain.VideoTag;
import com.sejong.capstone.repository.VideoRepository;
import com.sejong.capstone.repository.VideoTagRepository;
import com.sejong.capstone.service.VideoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoApiController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;
    private final VideoTagRepository videoTagRepository;

    /**
     * 영상 제공자가 비디오 업로드하면 해당 비디오 스토리지,DB에 저장후 FastAPI 측으로 보내 반환받은 JSON DB에 저장까지 처리하는 핸들러
     * - 전체 작업을 동기-비동기-동기로 쪼개서 처리(JPA와 WebFlux 동시 사용시 충돌문제 해결 위함)
     * 1.스토리지 및 DB에 비디오 저장(동기)
     * 2.FastAPI에 요청 및 JSON데이터 응답(비동기)
     * 3.응답받은 JSON데이터 DB에 저장(동기)
     */
    @PostMapping("/api/video")
    public Long videoUpload(@SessionAttribute(name = "loginMember") Member loginMember, @ModelAttribute VideoForm videoForm) throws IOException {
        Long videoId = videoService.saveVideo(loginMember.getId(), videoForm);
        videoService.communicateWithFastAPIAsync(videoId, videoForm)
                .thenAccept(jsonResponse -> videoService.jsonParsing(videoId, jsonResponse));
        return videoId;
    }

    /**
     * 전체적인 비디오 관련 정보들을 화면에 렌더링하기 위해 데이터를 전달해주는 핸들러
     * - 접근시마다 조회수 +1
     */
    @GetMapping("/api/video/{videoId}")
    public VideoResponse getVideoInfo(@SessionAttribute("loginMember") Member loginMember, @PathVariable("videoId") Long videoId) {
        log.info("videoID = {}", videoId);
        videoService.addViews(videoId);
        return videoService.getVideoInfos(videoId, loginMember.getId());
    }

    /**
     * 비디오 좋아요 처리하는 핸들러
     */
    @GetMapping("/api/video/like")
    public ResponseEntity setLikeVideo(@SessionAttribute(name = "loginMember") Member loginMember, @RequestParam("videoId") Long videoId) {
        int like = videoService.setLikeVideo(loginMember, videoId);
        return ResponseEntity.ok(like);
    }

    /**
     * 추천 영상 제공을 위한 API 처리하는 핸들러
     */
    @GetMapping("/api/video/recommend")
    public List<RecommendVideoResponse> getVideoListForRecommend(@RequestParam("tag") String tag) {
        List<Video> allForRecommend = videoRepository.findAllOrderByUploadDateDesc().stream()
                .filter(video -> !(video.getSubtitleSentences().isEmpty()))
                .collect(Collectors.toList());
        if (tag.equals("all")) {
            return allForRecommend.stream()
                    .map(video -> new RecommendVideoResponse(video))
                    .collect(Collectors.toList());
        } else {
            return allForRecommend.stream()
                    .filter(video -> video.getVideoTags().stream()
                            .anyMatch(videoTag -> videoTag.getName().equals("#" + tag)))
                    .map(video -> new RecommendVideoResponse(video))
                    .collect(Collectors.toList());
        }
    }

    /**
     * 가장 많이 사용된 Video Tag TOP5 제공하는 핸들러
     */
    @GetMapping("/api/video/mostTag")
    public ResponseEntity getMostTag() {
        Pageable pageable = PageRequest.of(0, 5);
        List<String> top5MostUsed = videoTagRepository.findTop5MostUsed(pageable);
        return ResponseEntity.ok(new VideoTagResponse(top5MostUsed));
    }
}