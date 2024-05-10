package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.PostRequestDto;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import com.sejong.capstone.domain.dto.PostInfo;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.PostRepository;
import com.sejong.capstone.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PostApiController {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final VideoRepository videoRepository;

    @PostMapping("/api/post")
    public Long savePost(@RequestBody PostRequestDto postRequestDto) {
        Member member = memberRepository.findById(1L).orElseThrow();
        Video video = videoRepository.findById(36L).orElseThrow();
        Post post = Post.createPost(member, video, postRequestDto.getTags(), new PostInfo(postRequestDto.getTitle(), postRequestDto.getContent()));
        Post savedPost = postRepository.save(post);
        return savedPost.getId();
    }
}
