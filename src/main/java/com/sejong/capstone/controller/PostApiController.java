package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostListResponse;
import com.sejong.capstone.controller.dto.PostRequestDto;
import com.sejong.capstone.controller.dto.PostResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.domain.dto.PostInfo;
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

    @PostMapping("/api/post")
    public Long savePost(@SessionAttribute(name = "loginMember") Member loginMember, @RequestBody PostRequestDto postRequestDto) {
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow();
        Video video = null;

        //일반 게시글 작성(비디오url 없이)
        if (postRequestDto.getVideoId() != null) {
            video = videoRepository.findById(postRequestDto.getVideoId()).orElseThrow();
        }

        Post post = Post.createPost(member, video, postRequestDto.getTags(), new PostInfo(postRequestDto.getTitle(), postRequestDto.getContent()));
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }

    @GetMapping("/api/post")
    public PostListResponse postList() {
        List<PostResponse> resultList = postRepository.findAll().stream()
                .map(post -> new PostResponse(post))
                .collect(Collectors.toList());
        return new PostListResponse(resultList);
    }
}
