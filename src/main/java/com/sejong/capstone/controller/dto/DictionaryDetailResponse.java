package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 사전 API 처리 결과 응답 담기 위한 DTO
 */
@Data
@AllArgsConstructor
public class DictionaryDetailResponse {
    private Long id;    // 오역수정된 단어일 경우 mistranslation_word_id로, 아닐 경우 word_id로 사용
    private boolean isCorrected;
    private String wordName;
    private String wordMeaning;
}
