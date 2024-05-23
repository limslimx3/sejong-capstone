package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 가장 많이 사용한 TOP5 태그를 담기위한 DTO
 */
@Data
@AllArgsConstructor
public class VideoTagResponse {
    private List<String> top5Tags;
}
