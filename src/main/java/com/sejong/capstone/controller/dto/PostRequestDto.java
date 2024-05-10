package com.sejong.capstone.controller.dto;

import lombok.Data;

import java.util.List;

@Data
public class PostRequestDto {
    private String title;
    private String content;
    private List<String> tags;
}
