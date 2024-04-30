package com.sejong.capstone.domain;

import com.sejong.capstone.domain.dto.MemberInfo;
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

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

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

    /**
     * 생성 메서드
     */
    public static Member createMember(MemberInfo memberForm) {
        Member member = new Member();
        member.setMail(memberForm.getMail());
        member.setName(member.getName());
        member.setPassword(member.getPassword());
        member.setPhoneNum(member.getPhoneNum());
        member.setRole(memberForm.getRole());
        member.setStatus(MemberStatus.USING);

        return member;
    }
}
