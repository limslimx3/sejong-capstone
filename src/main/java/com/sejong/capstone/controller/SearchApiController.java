package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostDetailResponse;
import com.sejong.capstone.controller.dto.TotalSearchResponse;
import com.sejong.capstone.controller.dto.VideoSearchResponse;
import com.sejong.capstone.repository.PostRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class SearchApiController {

    private static final Logger log = LoggerFactory.getLogger(SearchApiController.class);
    private final PostRepository postRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/api/search")
    public TotalSearchResponse searchVideoAndPost(@RequestParam("query") String keyword) {
        List<PostDetailResponse> postDetailResponseList = postRepository.findAllPostByKeyword(keyword).stream()
                .map(post -> new PostDetailResponse(post))
                .collect(Collectors.toList());

        List<VideoSearchResponse> videoSearchResponseList = videoRepository.findAllVideoByKeyword(keyword).stream()
                .map(video -> new VideoSearchResponse(video))
                .collect(Collectors.toList());

        return new TotalSearchResponse(postDetailResponseList, videoSearchResponseList);
    }
}
