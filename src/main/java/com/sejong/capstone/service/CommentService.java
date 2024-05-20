package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.CommentSaveRequest;
import com.sejong.capstone.domain.Comment;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.repository.CommentRepository;
import com.sejong.capstone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Transactional
    public Long saveComment(Member loginMember, CommentSaveRequest request) {
        Comment parentComment = commentRepository.findById(request.getParentCommentId()).orElse(null);
        Post post = postRepository.findById(request.getPostId()).orElseThrow();
        Comment comment = Comment.createComment(loginMember, parentComment, request.getContent(), post);
        commentRepository.save(comment);
        return comment.getId();
    }
}
