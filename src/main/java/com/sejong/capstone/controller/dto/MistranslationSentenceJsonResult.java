package com.sejong.capstone.controller.dto;

import lombok.Data;

/**
 * FastAPI로부터 받은 JSON 오역 수정된 한영자막 문장정보 담기 위한 DTO
 */
@Data
public class MistranslationSentenceJsonResult {
    private Long subtitleSentenceId;
    private String correctedKorSentence;
    private String correctedEngSentence;
}
