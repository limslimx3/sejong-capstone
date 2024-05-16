package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleSentence;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DB로부터 조회한 SubtitleSentence 데이터들을 React 측으로 API 전달하기 위해 사용하는 DTO
 */
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
