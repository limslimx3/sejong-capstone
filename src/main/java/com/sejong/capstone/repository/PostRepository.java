package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p JOIN FETCH p.video WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword% OR p.video.title LIKE %:keyword% OR p.video.content LIKE %:keyword%")
    List<Post> findAllPostAndVideoByKeyword(@Param("keyword") String keyword);
}
