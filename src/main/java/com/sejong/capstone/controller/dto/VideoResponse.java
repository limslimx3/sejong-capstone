package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 특정 비디오 재생에 필요한 데이터들을 담기 위한 DTO
 *  - 비디오 관련 정보 뿐만 아니라 자막 도메인 데이터들도 담고있음
 */
@Builder
@AllArgsConstructor
@Data
public class VideoResponse {
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
