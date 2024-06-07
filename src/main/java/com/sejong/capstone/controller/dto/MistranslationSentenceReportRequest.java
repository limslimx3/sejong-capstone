package com.sejong.capstone.controller.dto;

import lombok.Data;

/**
 * React 측에서 신고된 문장의 Id값과 제공자가 수정한 문장을 담기 위한 DTO
 */
@Data
public class MistranslationSentenceReportRequest {
    private Long subtitleSentenceId;
    private String correctedKorSentence;
}
