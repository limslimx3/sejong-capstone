package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.JsonResponse;
import com.sejong.capstone.controller.dto.VideoForm;
import com.sejong.capstone.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
public class VideoApiController {

    private final VideoService videoService;

    @PostMapping("/api/video")
    public void videoUpload(@ModelAttribute VideoForm videoForm) throws IOException {
        videoService.saveVideo(1L, videoForm);
    }

    @PostMapping("/api/json")
    public Long getJSONFromFastAPI(@RequestBody JsonResponse json) {
        return videoService.jsonParsing(json);
    }
}
