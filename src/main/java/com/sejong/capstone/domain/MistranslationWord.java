package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "mistranslation_word")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class MistranslationWord {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mistranslation_word_id")
    private Long id;

    @Column(name = "is_corrected", nullable = false)
    private boolean corrected;

    @Column(name = "corrected_meaning")
    private String correctedMeaning;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_word_id", nullable = false)
    private SubtitleWord subtitleWord;

    /**
     * 생성 메서드
     */
    public static MistranslationWord createMistranslationWord(boolean isCorrected, SubtitleWord subtitleWord) {
        MistranslationWord mistranslationWord = new MistranslationWord();
        mistranslationWord.setCorrected(isCorrected);
        mistranslationWord.setSubtitleWord(subtitleWord);
        return mistranslationWord;
    }

    /**
     * 편의 메서드
     */
    public void correctMistranslationWord(String correctedMeaning) {
        this.corrected = true;
        this.correctedMeaning = correctedMeaning;
    }
}
