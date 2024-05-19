package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Word {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "word_id")
    private Long id;

    @Column(name = "word_name", nullable = false)
    private String wordName;

    @Column(name = "word_meaning", nullable = false)
    private String wordMeaning;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_word_id")
    private SubtitleWord subtitleWord;

    public static Word createWord(String wordName, String wordMeaning, SubtitleWord subtitleWord) {
        Word word = new Word();
        word.wordName = wordName;
        word.wordMeaning = wordMeaning;
        word.subtitleWord = subtitleWord;
        return word;
    }
}
