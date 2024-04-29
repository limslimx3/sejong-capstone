package com.sejong.capstone.domain;

import com.sejong.capstone.domain.dto.VideoInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class Video {

    @Id @GeneratedValue
    @Column(name = "video_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    private String content;

    @Column(name = "path_video", nullable = false)
    private String pathVideo;

    @Column(name = "path_pic")
    private String pathPic;

    //좋아요
    @Column(name = "likes")
    private int like;

    //조회수
    private int views;

    @Column(name = "upload_date")
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @OneToMany(mappedBy = "video")
    private List<VideoTag> videoTags = new ArrayList<>();

    @OneToMany(mappedBy = "video")
    private List<SubtitleSentence> subtitleSentences = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getVideos().add(this);
    }

    public void addTags(VideoTag videoTag) {
        this.videoTags.add(videoTag);
        videoTag.setVideo(this);
    }

    /**
     * 생성 메서드
     * 비디오 도메인 생성시에는 사용자 입력 Form Data만으로 생성하도록 한다.
     * 추후 FastAPI 측으로부터 받은 자막 정보 등은 setter메서드를 통해 값을 변경하는 방식으로 JPA의 변경감지를 이용하여 DB에 저장한다
     */
    public static Video createVideo(Member member, VideoInfo videoInfo) {
        Video video = new Video();
        video.setTitle(videoInfo.getTitle());
        video.setContent(videoInfo.getContent());
        video.setPathVideo(videoInfo.getVideoPath());
        video.setPathPic(videoInfo.getThumbnailPath());
        video.setUploadDate(LocalDateTime.now());

        for (VideoTag videoTag : videoInfo.getVideoTags()) {
            video.addTags(videoTag);
        }

        video.setMember(member);

        return video;
    }
}
