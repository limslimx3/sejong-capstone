package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class SearchResponseDto {
    private List<PostResponse> postResponseList;
    private List<VideoResponse> videoResponseList;

    public SearchResponseDto(List<PostResponse> postResponseList, List<VideoResponse> videoResponseList) {
        this.postResponseList = postResponseList;
        this.videoResponseList = videoResponseList;
    }
}
