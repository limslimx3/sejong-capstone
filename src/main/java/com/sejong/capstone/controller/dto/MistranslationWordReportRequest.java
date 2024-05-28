package com.sejong.capstone.controller.dto;

import lombok.Data;

/**
 * React 측에서 신고된 단어의 Id값과 제공자가 수정한 뜻을 담기 위한 DTO
 */
@Data
public class MistranslationWordReportRequest {
    private Long subtitleWordId;
    private String correctedMeaning;
}
