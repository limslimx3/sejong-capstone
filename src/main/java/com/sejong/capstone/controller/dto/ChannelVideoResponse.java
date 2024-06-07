package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.MistranslationSentence;
import com.sejong.capstone.domain.MistranslationWord;
import com.sejong.capstone.domain.Video;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 각 채널에 해당하는 비디오 정보들을 담기 위한 DTO
 */
@Data
public class ChannelVideoResponse {
    private Long videoId;
    private String videoTitle;
    private int likes;
    private int views;
    private Integer mistranslationSentenceCount;
    private Integer mistranslationWordCount;
    private LocalDateTime uploadedAt;
    private boolean isSubtitleCreated;
    private List<String> videoTags;

    public ChannelVideoResponse(Video video, List<MistranslationSentence> mistranslationSentences, List<MistranslationWord> mistranslationWords) {
        this.videoId = video.getId();
        this.videoTitle = video.getTitle();
        this.likes = video.getLike();
        this.views = video.getViews();
        this.mistranslationSentenceCount = (int) mistranslationSentences.stream()
                .filter(mistranslationSentence -> mistranslationSentence.getSubtitleSentence().getVideo().getId().equals(video.getId()) && !mistranslationSentence.isCorrected())
                .count();
        this.mistranslationWordCount = (int) mistranslationWords.stream()
                .filter(mistranslationWord -> mistranslationWord.getSubtitleWord().getSubtitleSentence().getVideo().getId().equals(video.getId()) && !mistranslationWord.isCorrected())
                .count();
        this.uploadedAt = video.getUploadDate();
        this.isSubtitleCreated = !(video.getSubtitleSentences().isEmpty());
        this.videoTags = video.getVideoTags().stream()
                .map(videoTag -> videoTag.getName())
                .collect(Collectors.toList());
    }
}
