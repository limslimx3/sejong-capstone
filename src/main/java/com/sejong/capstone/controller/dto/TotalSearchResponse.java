package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 통합검색시 응답 데이터 담기 위한 DTO
 */
@Data
public class TotalSearchResponse {
    private List<PostResponse> postResponseList;
    private List<VideoSearchResponse> videoSearchResponseList;

    public TotalSearchResponse(List<PostResponse> postResponseList, List<VideoSearchResponse> videoSearchResponseList) {
        this.postResponseList = postResponseList;
        this.videoSearchResponseList = videoSearchResponseList;
    }
}
