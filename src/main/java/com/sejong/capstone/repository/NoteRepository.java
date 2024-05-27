package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Note;
import com.sejong.capstone.domain.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Long> {

    @Query("select n from Note n join fetch n.word w join fetch w.subtitleWord sw join fetch sw.subtitleSentence ss join fetch ss.video v where n.member.id = :id")
    List<Note> findAllNoteSubtitleVideoByMemberId(@Param("id") Long id);
}