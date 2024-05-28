package com.sejong.capstone.repository;

import com.sejong.capstone.domain.MistranslationWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MistranslationWordRepository extends JpaRepository<MistranslationWord, Long> {

    @Query("select mw from MistranslationWord mw join fetch mw.subtitleWord sw join fetch sw.subtitleSentence ss join fetch ss.video v join fetch v.member m")
    List<MistranslationWord> findAllFetchJoin();

    @Query("select mw from MistranslationWord mw join fetch mw.subtitleWord sw where sw.id = :subtitleWordId")
    Optional<MistranslationWord> findBySubtitleWordId(@Param("subtitleWordId") Long subtitleWordId);
}
