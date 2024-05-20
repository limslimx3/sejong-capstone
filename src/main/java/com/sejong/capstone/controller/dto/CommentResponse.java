package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Comment;
import lombok.Data;

@Data
public class CommentResponse {
    private Long commentId;
    private Long parentCommentId;
    private String commentContent;
    private String commentWriter;
    private int commentLike;
    private int commentDislike;

    public CommentResponse(Comment comment) {
        this.commentId = comment.getId();
        this.parentCommentId = comment.getParent().getId();
        this.commentContent = comment.getContent();
        this.commentWriter = comment.getMember().getName();
        this.commentLike = comment.getLike();
        this.commentDislike = comment.getDislike();
    }
}
