package com.sejong.capstone.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class DictionaryResponse {
    List<DictionaryDetailResponse> dictionaryDetailResponses;
}
