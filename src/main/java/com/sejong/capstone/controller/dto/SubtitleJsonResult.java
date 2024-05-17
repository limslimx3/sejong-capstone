package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * FastAPI로부터 받은 JSON 데이터 파싱시에 자막 문장정보 담기 위한 DTO
 */
@Data
public class SubtitleJsonResult {
    private Long subtitleId;
    private float start;
    private float end;
    private String korText;
    private List<String> korWordText;
    private String engText;
}
