package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Note;
import lombok.Data;

/**
 * 특정 사용자의 Note 정보들을 담기 위한 DTO2
 */
@Data
public class NoteInnerResponse {
    private Long noteId;
    private String wordName;
    private String wordMeaning;
    private Long videoId;
    private float timeline;

    public NoteInnerResponse(Note note) {
        this.noteId = note.getId();

        if (note.getWord() == null) {
            this.wordName = note.getMistranslationWord().getSubtitleWord().getKorWord();
            this.wordMeaning = note.getMistranslationWord().getCorrectedMeaning();
            this.videoId = note.getMistranslationWord().getSubtitleWord().getSubtitleSentence().getVideo().getId();
            this.timeline = note.getMistranslationWord().getSubtitleWord().getSubtitleSentence().getTimeline();
        } else if (note.getMistranslationWord() == null) {
            this.wordName = note.getWord().getWordName();
            this.wordMeaning = note.getWord().getWordMeaning();
            this.videoId = note.getWord().getSubtitleWord().getSubtitleSentence().getVideo().getId();
            this.timeline = note.getWord().getSubtitleWord().getSubtitleSentence().getTimeline();
        }
    }
}
