package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Video;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 특정 비디오 재생에 필요한 데이터들을 담기 위한 DTO
 *  - 비디오 관련 정보 뿐만 아니라 자막,커뮤니티 도메인 데이터들도 담고있음
 */
@Data
public class VideoResponse {
    private Long videoId;
    private String title;
    private String content;
    private int like;
    private int views;
    private LocalDateTime uploadDate;
    private String providerName;
    private boolean isLiked;
    private List<String> videoTags;
    private List<SubtitleSentenceResponse> subtitleSentences;
    private List<CommunityResponse> communityResponses;


    public VideoResponse(Video video, Long memberId) {
        this.videoId = video.getId();
        this.title = video.getTitle();
        this.content = video.getContent();
        this.like = video.getLike();
        this.views = video.getViews();
        this.uploadDate = video.getUploadDate();
        this.providerName = video.getMember().getName();
        this.isLiked = video.getVideoLikes().stream()
                .anyMatch(videoLike -> videoLike.getMember().getId().equals(memberId));
        this.videoTags = video.getVideoTags().stream()
                .map(videoTag -> videoTag.getName())
                .collect(Collectors.toList());
        this.subtitleSentences = video.getSubtitleSentences().stream()
                .map(subtitleSentence -> new SubtitleSentenceResponse(subtitleSentence))
                .collect(Collectors.toList());
        this.communityResponses = video.getPosts().stream()
                .map(post -> new CommunityResponse(post))
                .collect(Collectors.toList());
    }
}
