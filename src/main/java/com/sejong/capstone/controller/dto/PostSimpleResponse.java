package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 커뮤니티 글 목록 조회시에 각 커뮤니티 정보 담기 위한 DTO
 */
@Data
public class PostSimpleResponse {
    private Long postId;
    private String title;
    private String content;
    private int like;
    private List<String> postTags;

    public PostSimpleResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.like = post.getLike();
        this.postTags = post.getPostTags().stream()
                .map(postTag -> postTag.getName())
                .collect(Collectors.toList());
    }
}
