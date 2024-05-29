package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Note;
import com.sejong.capstone.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n left join fetch n.word w left join fetch w.subtitleWord sw1 left join fetch sw1.subtitleSentence ss1 left join fetch ss1.video v1 left join fetch n.mistranslationWord mw left join fetch mw.subtitleWord sw2 left join fetch sw2.subtitleSentence ss2 left join fetch ss2.video v2 where n.member.id = :id")
    List<Note> findAllNoteSubtitleVideoByMemberId(@Param("id") Long id);
}
