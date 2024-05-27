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

    //join fetch가 아닌 left join fetch를 통해 N+1 문제를 해결함과 동시에 video가 null값인 경우도 조회할 수 있도록 함
    @Query("select p from Post p join fetch p.member m left join fetch p.video v order by p.updatedAt desc")
    List<Post> findAllPostMemberVideoOrderByUploadDate();
}
