package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Post;
import com.sejong.capstone.domain.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v join fetch v.member m join fetch v.videoTags vt where v.id = :videoId")
    Video findByIdUsingFetchJoin(@Param("videoId") Long videoId);

    @Query("select v from Video v")
    List<Video> findAllForRecommend();

    @Query("SELECT v FROM Video v WHERE v.title LIKE %:keyword% OR v.content LIKE %:keyword%")
    List<Video> findAllVideoByKeyword(@Param("keyword") String keyword);
}
