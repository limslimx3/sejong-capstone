package com.sejong.capstone.repository;

import com.sejong.capstone.domain.SubtitleSentence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubtitleSentenceRepository extends JpaRepository<SubtitleSentence, Long> {

    @Query("select ss from SubtitleSentence ss join fetch ss.mistranslationSentences ms where ss.video.member.id = :memberId and ss.video.id = :videoId and ms.corrected = false")
    List<SubtitleSentence> findAllSubtitleSentenceByMemberAndVideo(@Param("memberId") Long memberId, @Param("videoId") Long videoId);
}
