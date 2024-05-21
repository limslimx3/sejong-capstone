package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 커뮤니티 글 전체 목록 응답 담기 위한 DTO
 */
@Data
@AllArgsConstructor
public class PostListResponse {
    private List<PostSimpleResponse> postSimpleResponses;
}
