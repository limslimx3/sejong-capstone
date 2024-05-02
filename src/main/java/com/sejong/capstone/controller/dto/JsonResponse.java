package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class JsonResponse {
    private Long videoId;
    private List<SubtitleResponse> subtitleList;
}
