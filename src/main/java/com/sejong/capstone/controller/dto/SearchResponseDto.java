package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import lombok.Data;

import java.util.List;

public class SearchResponseDto {
    private List<PostResponseDto> postResponseDtoList;
    private List<VideoResponseDto> videoResponseDtoList;

    public SearchResponseDto(List<PostResponseDto> postResponseDtoList, List<VideoResponseDto> videoResponseDtoList) {
        this.postResponseDtoList = postResponseDtoList;
        this.videoResponseDtoList = videoResponseDtoList;
    }
}
