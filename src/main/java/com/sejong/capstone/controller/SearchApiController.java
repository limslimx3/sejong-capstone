package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostResponseDto;
import com.sejong.capstone.controller.dto.SearchResponseDto;
import com.sejong.capstone.controller.dto.VideoResponseDto;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.repository.PostRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class SearchApiController {

    private static final Logger log = LoggerFactory.getLogger(SearchApiController.class);
    private final PostRepository postRepository;
    private final VideoRepository videoRepository;

    @GetMapping("/api/search")
    public SearchResponseDto searchVideoAndPost(@RequestParam("query") String keyword) {
        List<PostResponseDto> postResponseDtoList = postRepository.findAllPostByKeyword(keyword).stream()
                .map(post -> new PostResponseDto(post))
                .collect(Collectors.toList());

        List<VideoResponseDto> videoResponseDtoList = videoRepository.findAllVideoByKeyword(keyword).stream()
                .map(video -> new VideoResponseDto(video))
                .collect(Collectors.toList());

        return new SearchResponseDto(postResponseDtoList, videoResponseDtoList);
    }
}
