package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Comment;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.PostTag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 커뮤니티 글 조회 응답 데이터 담기 위한 DTO
 *  - 커뮤니티 뿐만 아니라 통합검색 기능에서도 응답 담기 위해 사용함
 */
@Slf4j
@Data
public class PostDetailResponse {
    private Long postId;
    private String postTitle;
    private String postContent;
    private String postWriter;
    private List<String> postTags;
    private List<CommentResponse> comments;
    private int postLike;

    public PostDetailResponse(Post post) {
        this.postId = post.getId();
        this.postTitle = post.getTitle();
        this.postContent = post.getContent();
        this.postWriter = post.getMember().getMemberId();

        this.postTags = post.getPostTags().stream()
                .map(PostTag::getName)
                .collect(Collectors.toList());

        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());

        this.postLike = post.getLike();
    }
}
