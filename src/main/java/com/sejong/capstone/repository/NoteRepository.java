package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Note;
import com.sejong.capstone.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n join fetch n.word w join fetch w.subtitleWord sw1 join fetch sw1.subtitleSentence ss1 join fetch ss1.video v1 join fetch n.mistranslationWord mw join fetch mw.subtitleWord sw2 join fetch sw2.subtitleSentence ss2 join fetch ss2.video v2 where n.member.id = :id")
    List<Note> findAllNoteSubtitleVideoByMemberId(@Param("id") Long id);
}
