package com.sejong.capstone.repository;

import com.sejong.capstone.domain.VideoTag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface VideoTagRepository extends JpaRepository<VideoTag, Long> {

    @Query("SELECT vt.name FROM VideoTag vt GROUP BY vt.name ORDER BY COUNT(*) DESC")
    List<String> findTop5MostUsed(Pageable pageable);
}
