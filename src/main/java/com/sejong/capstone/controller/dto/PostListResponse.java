package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PostListResponse {
    private List<PostResponse> postResponseList;
}
