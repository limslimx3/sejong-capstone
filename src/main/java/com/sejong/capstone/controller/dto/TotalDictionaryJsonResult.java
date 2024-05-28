package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * 사전 API 결과 반환된 한국어 단어에 대한 영어 뜻을 담기 위한 DTO
 */
@AllArgsConstructor
@Data
public class TotalDictionaryJsonResult {
    private String word;
    private List<MeaningCrawlingJsonResult> engMeanings;
}
