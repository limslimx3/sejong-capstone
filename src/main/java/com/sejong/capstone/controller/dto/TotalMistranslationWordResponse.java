package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.MistranslationWord;
import com.sejong.capstone.domain.SubtitleWord;
import lombok.Data;

/**
 * 오역 단어를 포함한 모든 SubtitleWord 값을 담기 위한 DTO
 */
@Data
public class TotalMistranslationWordResponse {
    private Long subtitleWordId;
    private String word;

    public TotalMistranslationWordResponse(MistranslationWord mistranslationWord) {
        this.subtitleWordId = mistranslationWord.getSubtitleWord().getId();
        this.word = mistranslationWord.getSubtitleWord().getKorWord();
    }
}
