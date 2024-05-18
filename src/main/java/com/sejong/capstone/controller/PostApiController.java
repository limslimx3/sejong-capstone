package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostListResponse;
import com.sejong.capstone.controller.dto.PostSaveRequest;
import com.sejong.capstone.controller.dto.PostResponse;
import com.sejong.capstone.controller.dto.PostUpdateRequest;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.PostRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    /**
     * 커뮤니티 글 저장
     */
    @PostMapping("/api/post")
    public Long savePost(@SessionAttribute(name = "loginMember") Member loginMember, @RequestBody PostSaveRequest postSaveRequest) {
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow();
        Video video = null;

        //일반 게시글 작성(비디오url 없이)
        if (postSaveRequest.getVideoId() != null) {
            video = videoRepository.findById(postSaveRequest.getVideoId()).orElseThrow();
        }

        Post post = Post.createPost(member, video, postSaveRequest.getTitle(), postSaveRequest.getContent(), postSaveRequest.getTags());
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    /**
     * 커뮤니티 글 목록 조회
     */
    @GetMapping("/api/post")
    public PostListResponse postList() {
        List<PostResponse> resultList = postRepository.findAll().stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
        return new PostListResponse(resultList);
    }

    /**
     * 커뮤니티 글 상세 조회
     */
    @GetMapping("/api/post/{id}")
    public PostResponse postDetail(@PathVariable("id") Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        return new PostResponse(post);
    }

    @PutMapping("/api/post/{id}")
    public Long postUpdate(@PathVariable("id") Long postId, @RequestBody PostUpdateRequest postUpdateRequest) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.updatePost(post, postUpdateRequest.getTitle(), postUpdateRequest.getContent(), post.getPostTags(), postUpdateRequest.getTags());
        return post.getId();
    }

    @DeleteMapping("/api/post/{id}")
    public void postDelete(@PathVariable("id") Long postId) {
        postRepository.deleteById(postId);
    }
}
