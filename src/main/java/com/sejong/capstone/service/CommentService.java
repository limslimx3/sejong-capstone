package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.CommentSaveRequest;
import com.sejong.capstone.domain.Comment;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.repository.CommentRepository;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long saveComment(Long memberId, CommentSaveRequest request) {
        Optional<Comment> parentComment = Optional.ofNullable(request.getParentCommentId())
                .map(id -> commentRepository.findById(id))
                .orElse(Optional.empty());

        Post post = postRepository.findById(request.getPostId()).orElseThrow();
        Member member = memberRepository.findById(memberId).orElseThrow();
        Comment comment = Comment.createComment(member, parentComment.orElse(null), request.getContent(), post);
        commentRepository.save(comment);
        return comment.getId();
    }
}
