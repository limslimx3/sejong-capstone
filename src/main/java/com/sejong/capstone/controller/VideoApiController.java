package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.VideoForm;
import com.sejong.capstone.controller.dto.VideoInfoResponse;
import com.sejong.capstone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.MalformedURLException;

@RequiredArgsConstructor
@RestController
public class VideoApiController {

    private final VideoService videoService;

    @PostMapping("/api/video")
    public Long videoUpload(@ModelAttribute VideoForm videoForm) throws IOException {
        Long videoId = videoService.saveVideo(1L, videoForm);
        return videoService.communicateWithFastAPI(videoId);
    }

    /**
     * 전체적인 비디오 관련 정보들을 화면에 렌더링하기 위해 데이터를 전달해주는 핸들러
     */
    @GetMapping("/api/video/{videoId}")
    public VideoInfoResponse getVideoInfo(@PathVariable("videoId") Long videoId) {
        return videoService.getVideoInfos(videoId);
    }

    /**
     * 위에서 작성한 getVideoInfo 핸들러를 통해 전달해준 비디오 url에 접근하여 비디오를 재생할 수 있도록 처리하는 핸들러
     */
    @GetMapping("/api/video/{videoUrl}")
    public ResponseEntity<Resource> getVideoAndSubtitle(@PathVariable("videoUrl") String videoUrl) throws MalformedURLException {
        Resource videoFromStorage = videoService.getVideoFromStorage(videoUrl);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("video/mp4"))
                .body(videoFromStorage);
    }
}
