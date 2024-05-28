package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class Note {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "word_id")
    private Word word;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mistranslation_word_id")
    private MistranslationWord mistranslationWord;

    /**
     * 생성 메서드
     */
    public static Note createNote(Member member, Word word, MistranslationWord mistranslationWord) {
        Note note = new Note();
        note.setMember(member);
        note.setWord(word);
        note.setMistranslationWord(mistranslationWord);
        return note;
    }
}
