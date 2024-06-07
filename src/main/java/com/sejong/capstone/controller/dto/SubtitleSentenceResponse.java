package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleSentence;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * DB로부터 조회한 SubtitleSentence 데이터들을 React 측으로 API 전달하기 위해 사용하는 DTO
 */
@Data
public class SubtitleSentenceResponse {
    private Long subtitleId;
    private float startTime;
    private String korText;
    private String engText;
    private List<SubtitleWordResponse> korWords;

    public SubtitleSentenceResponse(SubtitleSentence subtitleSentence) {
        this.subtitleId = subtitleSentence.getId();
        this.startTime = subtitleSentence.getTimeline();
        this.korText = subtitleSentence.getKorSubtitle();
        this.engText = subtitleSentence.getEngSubtitle();
        this.korWords = subtitleSentence.getSubtitleWords().stream()
                .filter(subtitleWord -> subtitleWord.getSubtitleWordVer() != 0)
                .map(subtitleWord -> new SubtitleWordResponse(subtitleWord))
                .collect(Collectors.toList());
    }
}
