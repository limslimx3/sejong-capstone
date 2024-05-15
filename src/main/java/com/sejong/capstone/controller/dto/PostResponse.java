package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.PostTag;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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

        List<PostTag> postTags1 = post.getPostTags();
        for (PostTag postTag : postTags1) {
            log.info("postTag name = {}", postTag.getName());
        }
        post.getPostTags().forEach(postTag -> postTags.add(postTag.getName()));
        this.likes = post.getLike();
        this.dislikes = post.getDislike();
    }
}
