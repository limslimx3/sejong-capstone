package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Video;
import com.sejong.capstone.domain.VideoTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VideoRepository extends JpaRepository<Video, Long> {

    @Query("select v from Video v join fetch v.member m where v.id = :videoId")
    Video findByIdUsingFetchJoin(@Param("videoId") Long videoId);

    @Query("select v from Video v order by v.uploadDate desc")
    List<Video> findAllOrderByUploadDateDesc();

    @Query("SELECT v FROM Video v WHERE v.title LIKE %:keyword% OR v.content LIKE %:keyword%")
    List<Video> findAllVideoByKeyword(@Param("keyword") String keyword);
}
