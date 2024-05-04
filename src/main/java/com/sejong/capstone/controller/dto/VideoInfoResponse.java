package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleSentence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 프론트 렌더링을 위해 Spring에서 전달하는 데이터를 담는 DTO
 */
@Builder
@AllArgsConstructor
@Data
public class VideoInfoResponse {
    private Long videoId;
    private String title;
    private String content;
    private String videoPath;
    private int like;
    private int views;
    private LocalDateTime uploadDate;
    private String providerName;
    private List<String> videoTags;
    private List<SubtitleSentenceResponse> subtitleSentences;
}
