package com.sejong.capstone.repository;

import com.sejong.capstone.domain.VideoLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VideoLikeRepository extends JpaRepository<VideoLike, Long> {
    Optional<VideoLike> findByMemberIdAndVideoId(Long memberId, Long videoId);
}
