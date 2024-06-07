package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.MistranslationSentence;
import com.sejong.capstone.domain.MistranslationWord;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 특정 사용자의 채널 관련 정보들을 담기 위한 DTO
 */
@Data
public class ChannelInfoResponse {
    private String channelName;
    private int likes;
    private LocalDateTime channelCreatedAt;
    private List<ChannelVideoResponse> channelVideos;

    public ChannelInfoResponse(Member member, List<MistranslationSentence> mistranslationSentences, List<MistranslationWord> mistranslationWords) {
        this.channelName = member.getChannel().getChannelName();
        this.likes = member.getChannel().getLikes();
        this.channelCreatedAt = member.getChannel().getCreatedAt();
        if (member.getVideos().isEmpty()) {
            this.channelVideos = null;
        } else {
            this.channelVideos = member.getVideos().stream()
                    .map(video -> new ChannelVideoResponse(video, mistranslationSentences, mistranslationWords))
                    .collect(Collectors.toList());
        }
    }
}
