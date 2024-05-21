package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 사용자 학습 화면에 비디오 관련된 커뮤니티 게시글 보여주기 위해 사용하는 DTO
 */
@Data
public class CommunityResponse {
    private Long postId;
    private String title;
    private String content;
    private int like;
    private LocalDateTime uploadDate;
    private List<String> postTags;
    private int commentCnt;

    public CommunityResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.like = post.getLike();
        this.uploadDate = post.getUpdatedAt();
        this.postTags = post.getPostTags().stream()
                .map(postTag -> postTag.getName())
                .collect(Collectors.toList());
        this.commentCnt = post.getComments().size();
    }
}
