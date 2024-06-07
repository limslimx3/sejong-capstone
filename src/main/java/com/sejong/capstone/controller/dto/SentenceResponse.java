package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 오역 문장을 포함한 모든 SubtitleSentence 리스트값을 담기 위한 DTO
 */
@Data
public class SentenceResponse {
    private List<TotalSentenceResponse> resultList;

    public SentenceResponse(List<TotalSentenceResponse> resultList) {
        this.resultList = resultList;
    }
}
