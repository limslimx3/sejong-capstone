package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.SubtitleWord;
import lombok.Data;

/**
 * 사용자 학습화면에서 각 단어의 Id값과 의미를 담기 위한 DTO
 */
@Data
public class SubtitleWordResponse {
    private Long subtitleWordId;
    private String subtitleWordName;

    public SubtitleWordResponse(SubtitleWord subtitleWord) {
        this.subtitleWordId = subtitleWord.getId();
        this.subtitleWordName = subtitleWord.getKorWord();
    }
}
