package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 네이버 사전을 크롤링한 결과 각 단어들에 대한 영어뜻을 담기 위한 DTO
 */
@Data
@AllArgsConstructor
public class MeaningCrawlingJsonResult {
    private String realWord;
    private String meaning;
}
