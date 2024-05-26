package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.etc.MemberRole;
import lombok.Data;

/**
 * 특정 사용자의 마이페이지 정보 담기 위한 DTO
 */
@Data
public class MyPageResponse {
    private Long id;
    private String memberId;
    private String name;
    private String email;
    private MemberRole role;

    public MyPageResponse(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.email = member.getMail();
        this.role = member.getRole();
    }
}
