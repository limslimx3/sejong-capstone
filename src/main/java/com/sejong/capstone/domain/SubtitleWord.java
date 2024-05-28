package com.sejong.capstone.domain;

import com.sejong.capstone.domain.etc.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subtitle_word")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class SubtitleWord extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subtitle_word_id")
    private Long id;

    @Setter
    @Column(name = "subtitle_word_ver", nullable = false)
    private int subtitleWordVer;

    @Column(nullable = false)
    private int seq;

    @Column(name = "word_kor")
    private String korWord;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_id")
    private SubtitleSentence subtitleSentence;

    /**
     * 연관관계 편의 메서드
     */
    public void setSubtitleSentence(SubtitleSentence subtitleSentence) {
        this.subtitleSentence = subtitleSentence;
        subtitleSentence.getSubtitleWords().add(this);
    }

    /**
     * 생성 메서드
     */
    public static SubtitleWord createSubtitleWord(int subtitleWordVer, int seq, String korWord, SubtitleSentence subtitleSentence) {
        SubtitleWord subtitleWord = new SubtitleWord();
        subtitleWord.setSubtitleWordVer(subtitleWordVer);
        subtitleWord.setSeq(seq);
        subtitleWord.setKorWord(korWord);

        subtitleWord.setSubtitleSentence(subtitleSentence);

        return subtitleWord;
    }
}
