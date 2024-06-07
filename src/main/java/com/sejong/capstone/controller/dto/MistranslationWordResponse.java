package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

/**
 * 오역 단어를 포함한 모든 SubtitleWord 리스트값을 담기 위한 DTO
 */

@Data
public class MistranslationWordResponse {
    private List<TotalMistranslationWordResponse> resultList;

    public MistranslationWordResponse(List<TotalMistranslationWordResponse> resultList) {
        this.resultList = resultList;
    }
}
