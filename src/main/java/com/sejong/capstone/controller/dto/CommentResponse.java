package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Comment;
import lombok.Data;

import java.util.Optional;

@Data
public class CommentResponse {
    private Long commentId;
    private Long parentCommentId;
    private String commentContent;
    private String commentWriter;
    private int commentLike;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.parentCommentId = Optional.ofNullable(comment.getParent())
                .map(parent->parent.getId())
                .orElse(null);
        this.commentContent = comment.getContent();
        this.commentWriter = comment.getMember().getName();
        this.commentLike = comment.getLike();
    }
}
