package com.sejong.capstone.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * React로부터 댓글 등록 요청을 받기 위한 DTO
 */
@Data
public class CommentSaveRequest {

    @NotNull
    private Long postId;

    private Long parentCommentId;

    @NotNull
    private String content;
}
