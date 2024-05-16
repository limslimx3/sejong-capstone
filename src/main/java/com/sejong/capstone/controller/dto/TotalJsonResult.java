package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * FastAPI로부터 받은 JSON 데이터 담기 위한 DTO
 */
@Data
public class TotalJsonResult {
    private Long videoId;
    private List<SubtitleJsonResult> subtitleList;
}
