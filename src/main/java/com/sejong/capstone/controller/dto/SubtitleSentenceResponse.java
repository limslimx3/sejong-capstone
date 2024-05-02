package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleSentence;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class SubtitleSentenceResponse {
    private Long subtitleId;
    private float startTime;
    private String korText;
    private String engText;

    public SubtitleSentenceResponse(SubtitleSentence subtitleSentence) {
        subtitleId = subtitleSentence.getId();
        startTime = subtitleSentence.getTimeline();
        korText = subtitleSentence.getKorSubtitle();
        engText = subtitleSentence.getEngSubtitle();
    }
}
