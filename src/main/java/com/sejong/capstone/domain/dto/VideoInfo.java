package com.sejong.capstone.domain.dto;

import com.sejong.capstone.domain.VideoTag;
import lombok.Getter;

import java.util.List;

@Getter
public class VideoInfo {
    private String title;
    private String content;
    private String videoPath;
    private String thumbnailPath;
    private List<VideoTag> videoTags;
}
