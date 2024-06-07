package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleSentence;
import lombok.Data;

/**
 * 오역 문장을 포함한 전체 자막을 담기 위한 DTO
 */
@Data
public class TotalSentenceResponse {
    private Long subtitleSentenceId;
    private String korSubtitle;
    private String engSubtitle;
    private float startTime;
    private boolean isReported;

    public TotalSentenceResponse(SubtitleSentence subtitleSentence) {
        this.subtitleSentenceId = subtitleSentence.getId();
        this.korSubtitle = subtitleSentence.getKorSubtitle();
        this.engSubtitle = subtitleSentence.getEngSubtitle();
        this.startTime = subtitleSentence.getTimeline();
        this.isReported = !subtitleSentence.getMistranslationSentences().isEmpty();
    }
}
