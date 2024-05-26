package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.etc.MemberRole;
import lombok.Data;

/**
 * 사용자가 로그인한 후 React 측으로 넘겨주는 데이터를 담기 위한 DTO
 */
@Data
public class LoginResponse {
    private Long id;
    private String memberId;
    private String name;
    private String email;
    private MemberRole role;

    public LoginResponse(Member member) {
        this.id = member.getId();
        this.memberId = member.getMemberId();
        this.name = member.getName();
        this.email = member.getMail();
        this.role = member.getRole();
    }
}
