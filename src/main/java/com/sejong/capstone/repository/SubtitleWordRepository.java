package com.sejong.capstone.repository;

import com.sejong.capstone.domain.SubtitleWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubtitleWordRepository extends JpaRepository<SubtitleWord, Long> {

    @Modifying
    @Query("delete from SubtitleWord sw where sw.subtitleSentence.id = :subtitleId")
    void deleteBySubtitleId(@Param("subtitleId") Long subtitleId);

    @Query("select sw from SubtitleWord sw where sw.subtitleSentence.id = :subtitleSentenceId")
    List<SubtitleWord> findBySubtitleSentenceId(@Param("subtitleSentenceId") Long subtitleSentenceId);
}
