package com.sejong.capstone.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community_comment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class Comment {

    @Id @GeneratedValue
    @Column(name = "seq_comment")
    private Long id;

    private String content;

    //좋아요
    @Column(name = "likes")
    private int like;

    //싫어요
    @Column(name = "dislikes")
    private int dislike;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Member member;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    //대댓글 위한 속성1(최상위 댓글)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "par_seq")
    private Comment parent;

    //대댓글 위한 속성2(하위 대댓글)
    @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
    private List<Comment> childes = new ArrayList<>();

    /**
     * 연관관계 편의 메서드
     */
    public void setMember(Member member) {
        this.member = member;
        member.getComments().add(this);
    }

    /**
     * 생성 메서드
     */
    public static Comment createComment(Member member, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setContent(content);

        return comment;
    }
}
