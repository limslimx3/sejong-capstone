package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "mistranslation_sentence")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class MistranslationSentence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mistranslation_sentence_id")
    private Long id;

    @Setter
    @Column(name = "is_corrected", nullable = false)
    private boolean corrected;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_id", nullable = false)
    private SubtitleSentence subtitleSentence;

    /**
     * 연관관계 편의 메서드
     */
    public void setSubtitleSentence(SubtitleSentence subtitleSentence) {
        this.subtitleSentence = subtitleSentence;
        subtitleSentence.getMistranslationSentences().add(this);
    }

    /**
     * 생성 메서드
     */
    public static MistranslationSentence createMistranslationSentence(SubtitleSentence subtitleSentence) {
        MistranslationSentence mistranslationSentence = new MistranslationSentence();
        mistranslationSentence.setSubtitleSentence(subtitleSentence);
        mistranslationSentence.setCorrected(false);
        return mistranslationSentence;
    }
}
