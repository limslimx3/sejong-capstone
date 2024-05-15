package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.RecommendVideoResponse;
import com.sejong.capstone.controller.dto.VideoForm;
import com.sejong.capstone.controller.dto.VideoInfoResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.repository.VideoRepository;
import com.sejong.capstone.service.VideoService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController
public class VideoApiController {

    private final VideoService videoService;
    private final VideoRepository videoRepository;

    /**
     * 영상 제공자가 비디오 업로드하면 해당 비디오 스토리지,DB에 저장후 FastAPI 측으로 보내 반환받은 JSON DB에 저장까지 처리하는 핸들러
     */
    @PostMapping("/api/video")
    public Long videoUpload(@SessionAttribute(name = "loginMember") Member loginMember, @ModelAttribute VideoForm videoForm) throws IOException {
        Long videoId = videoService.saveVideo(loginMember.getId(), videoForm);
        return videoService.communicateWithFastAPI(videoId);
    }

    /**
     * 전체적인 비디오 관련 정보들을 화면에 렌더링하기 위해 데이터를 전달해주는 핸들러
     */
    @GetMapping("/api/video/{videoId}")
    public VideoInfoResponse getVideoInfo(@PathVariable("videoId") Long videoId) {
        log.info("videoID = {}", videoId);
        return videoService.getVideoInfos(videoId);
    }

    /**
     * 위에서 작성한 getVideoInfo 핸들러를 통해 전달해준 비디오 url에 접근하여 비디오를 재생할 수 있도록 처리하는 핸들러
     */
    @GetMapping("/api/video/play/{videoUrl}")
    public ResponseEntity<Resource> getVideoAndSubtitle(@PathVariable("videoUrl") String videoUrl) throws MalformedURLException {
        Resource videoFromStorage = videoService.getVideoFromStorage(videoUrl);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(videoFromStorage);
    }

    /**
     * 추천 영상 제공을 위한 API 처리하는 핸들러
     */
    @GetMapping("/api/video/recommend")
    public List<RecommendVideoResponse> getVideoListForRecommend() {
        List<Video> allForRecommend = videoRepository.findAllForRecommend();
        return allForRecommend.stream()
                .map(video -> new RecommendVideoResponse(video))
                .collect(Collectors.toList());
    }
}