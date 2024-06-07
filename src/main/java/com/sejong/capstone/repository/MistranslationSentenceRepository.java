package com.sejong.capstone.repository;

import com.sejong.capstone.domain.MistranslationSentence;
import com.sejong.capstone.domain.MistranslationWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MistranslationSentenceRepository extends JpaRepository<MistranslationSentence, Long> {

    @Query("select ms from MistranslationSentence ms join fetch ms.subtitleSentence ss join fetch ss.video v join fetch v.member m")
    List<MistranslationSentence> findAllFetchJoin();

    @Query("select ms from MistranslationSentence ms join fetch ms.subtitleSentence ss where ss.id = :subtitleSentenceId order by ms.id desc")
    List<MistranslationSentence> findBySubtitleSentenceId(@Param("subtitleSentenceId") Long subtitleSentenceId);

    @Query("select distinct ms from MistranslationSentence ms where ms.subtitleSentence.video.member.id = :id")
    List<MistranslationSentence> findByMemberId(@Param("id") Long id);
}
