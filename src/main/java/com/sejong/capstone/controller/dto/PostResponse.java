package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.PostTag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 커뮤니티 글 조회 응답 데이터 담기 위한 DTO
 *  - 커뮤니티 뿐만 아니라 통합검색 기능에서도 응답 담기 위해 사용함
 */
@Slf4j
@Data
public class PostResponse {
    private Long postId;
    private String title;
    private String content;
    private List<String> postTags = new ArrayList<>();
    private int likes;
    private int dislikes;

    public PostResponse(Post post) {
        this.postId = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();

        List<PostTag> postTagsList = post.getPostTags();
        for (PostTag postTag : postTagsList) {
            log.info("postTag name = {}", postTag.getName());
        }
        post.getPostTags().forEach(postTag -> postTags.add(postTag.getName()));
        this.likes = post.getLike();
        this.dislikes = post.getDislike();
    }
}
