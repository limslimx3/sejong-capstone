package com.sejong.capstone.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

//Post.createPost()의 파라미터값 단순화 위한 dto
@Getter
@AllArgsConstructor
public class PostInfo {
    private String title;
    private String content;
//    private int timeline;
}
