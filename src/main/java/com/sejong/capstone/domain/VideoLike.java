package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "videolikes")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class VideoLike {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "likes_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "video_id", nullable = false)
    private Video video;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    /**
     * 연관관계 편의 메서드
     */
    public void setVideo(Video video) {
        this.video = video;
        video.getVideoLikes().add(this);
    }

    /**
     * 생성 메서드
     */
    public static VideoLike createVideoLike(Video video, Member member) {
        VideoLike videoLike = new VideoLike();
        videoLike.setVideo(video);
        videoLike.member = member;
        return videoLike;
    }
}
