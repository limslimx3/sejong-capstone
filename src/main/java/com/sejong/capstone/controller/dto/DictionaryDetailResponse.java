package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DictionaryDetailResponse {
    private String wordName;
    private String wordMeaning;
}
