package com.sejong.capstone.domain;

import com.sejong.capstone.domain.etc.BaseEntity;
import com.sejong.capstone.domain.etc.MemberRole;
import com.sejong.capstone.domain.etc.MemberStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter @Setter(AccessLevel.PRIVATE)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone")
    private String phoneNum;

    private String mail;

    @Column(nullable = false)
    @Enumerated
    private MemberRole role;

    @Column(nullable = false)
    @Enumerated
    private MemberStatus status;

    @Column(name = "login_api")
    private String loginApi;

    @OneToMany(mappedBy = "member")
    private List<Video> videos = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Post> posts = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Comment> comments = new ArrayList<>();

    @Setter
    @OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
    private Channel channel;

    /**
     * 생성 메서드
     */
    public static Member createMember(String memberId, String encodedPassword, String name, String email, MemberRole role) {
        Member member = new Member();
        member.setMemberId(memberId);
        member.setPassword(encodedPassword);
        member.setName(name);
        member.setMail(email);
        member.setRole(role);
        member.setStatus(MemberStatus.USING);

        return member;
    }
}
