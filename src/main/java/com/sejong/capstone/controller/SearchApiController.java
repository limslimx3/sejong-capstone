package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostResponseDto;
import com.sejong.capstone.controller.dto.SearchResponseDto;
import com.sejong.capstone.controller.dto.VideoResponseDto;
import com.sejong.capstone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class SearchApiController {

    private final PostRepository postRepository;

    @GetMapping("/api/search")
    public SearchResponseDto searchVideoAndPost(@RequestParam("query") String keyword) {
        List<PostResponseDto> postResponseDtoList = new ArrayList<>();
        List<VideoResponseDto> videoResponseDtoList = new ArrayList<>();

        postRepository.findAllPostAndVideoByKeyword(keyword).stream()
                .forEach(post -> {
                    PostResponseDto postResponseDto = new PostResponseDto(post);
                    VideoResponseDto videoResponseDto = new VideoResponseDto(post.getVideo());
                    postResponseDtoList.add(postResponseDto);
                    videoResponseDtoList.add(videoResponseDto);
                });
        return new SearchResponseDto(postResponseDtoList, videoResponseDtoList);
    }
}
