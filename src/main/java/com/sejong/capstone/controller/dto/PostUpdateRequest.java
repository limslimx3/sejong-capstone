package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 커뮤니티 글 수정 요청 데이터 담기 위한 DTO
 */
@Data
public class PostUpdateRequest {
    private String title;
    private String content;
    private List<String> tags;
}
