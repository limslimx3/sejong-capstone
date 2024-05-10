package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import lombok.Data;

@Data
public class PostResponseDto {
    private Long postId;
    private String title;
    private String content;
    private int likes;
    private int dislikes;

    public PostResponseDto(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.likes = post.getLike();
        this.dislikes = post.getDislike();
    }
}
