package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.CommentSaveRequest;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

    private final CommentService commentService;

    @PostMapping("/api/comments")
    public ResponseEntity saveComment(@SessionAttribute("loginMember") Member loginMember, @RequestBody CommentSaveRequest request) {
        Long commentId = commentService.saveComment(loginMember, request);
        return ResponseEntity.ok(commentId);
    }
}
