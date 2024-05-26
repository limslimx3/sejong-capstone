package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.*;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.PostRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostRepository postRepository;
    private final VideoRepository videoRepository;
    private final MemberRepository memberRepository;

    /**
     * 커뮤니티 글 저장
     */
    @PostMapping("/api/post")
    public Long savePost(@SessionAttribute(name = "loginMember") Member loginMember, @RequestBody PostSaveRequest postSaveRequest) {
        Optional<Video> video = Optional.ofNullable(postSaveRequest.getVideoId())
                .map(id -> videoRepository.findById(id))
                .orElse(Optional.empty());

        Member member = memberRepository.findById(loginMember.getId()).orElseThrow();
        Post post = Post.createPost(member, video.orElse(null), postSaveRequest.getTitle(), postSaveRequest.getContent(), postSaveRequest.getTags());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    /**
     * 커뮤니티 글 목록 조회
     */
    @GetMapping("/api/post")
    public ResponseEntity postList() {
        List<PostSimpleResponse> resultList = postRepository.findAllPostMemberVideo().stream()
                .map(post -> new PostSimpleResponse(post))
                .collect(Collectors.toList());
        return ResponseEntity.ok(new PostListResponse(resultList));
    }

    /**
     * 커뮤니티 글 상세 조회
     */
    @GetMapping("/api/post/{id}")
    public ResponseEntity postDetail(@PathVariable("id") Long postId) {
        Post post = postRepository.findPostMemberCommentsPostTagsById(postId).orElseThrow();
        return ResponseEntity.ok(new PostDetailResponse(post));
    }

    /**
     * 커뮤니티 글 수정
     */
    @PutMapping("/api/post/{id}")
    public Long postUpdate(@PathVariable("id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.updatePost(post, postUpdateRequest.getTitle(), postUpdateRequest.getContent(), post.getPostTags(), postUpdateRequest.getTags());
        return post.getId();
    }

    /**
     * 커뮤니티 글 삭제
     */
    @DeleteMapping("/api/post/{id}")
    public void postDelete(@PathVariable("id") Long postId, @SessionAttribute("loginMember") Member loginMember) {
        if(loginMember == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }
        postRepository.deleteById(postId);
    }
}
