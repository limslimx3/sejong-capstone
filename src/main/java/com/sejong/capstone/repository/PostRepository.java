package com.sejong.capstone.repository;

import com.sejong.capstone.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%")
    List<Post> findAllPostByKeyword(@Param("keyword") String keyword);

    @Query("select p from Post p join fetch p.member m where p.id = :postId")
    Optional<Post> findPostMemberCommentsPostTagsById(@Param("postId") Long postId);

    @Query("select p from Post p join fetch p.member m join fetch p.video v")
    List<Post> findAllPostMemberVideo();
}
