package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 추천영상 목록 조회시 응답 데이터 담기 위한 DTO
 */
@Data
@AllArgsConstructor
public class RecommendVideoResponse {
    private Long videoId;
    private String title;
    private String content;
    private int likes;
    private int views;
    private String channelName;

    public RecommendVideoResponse(Video video) {
        this.videoId = video.getId();
        this.title = video.getTitle();
        this.content = video.getContent();
        this.likes = video.getLike();
        this.views = video.getViews();
        this.channelName = video.getMember().getChannel().getChannelName();
    }
}
