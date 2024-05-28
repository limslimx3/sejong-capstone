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

    @Column(name = "is_corrected", nullable = false)
    private boolean isCorrected;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subtitle_id", nullable = false)
    private SubtitleSentence subtitleSentence;
}
