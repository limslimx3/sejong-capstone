package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Video;
import lombok.Data;

import java.util.List;

@Data
public class VideoResponse {
    private Long videoId;
    private String title;
    private String content;
    private int likes;
    private int views;

    public VideoResponse(Video video) {
        this.videoId = video.getId();
        this.title = video.getTitle();
        this.content = video.getContent();
        this.likes = video.getLike();
        this.views = video.getViews();
    }
}
