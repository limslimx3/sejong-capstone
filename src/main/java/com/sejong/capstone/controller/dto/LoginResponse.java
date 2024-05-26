package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.Member;
import lombok.Data;

/**
 * 사용자가 로그인한 후 React 측으로 넘겨주는 데이터를 담기 위한 DTO
 */
@Data
public class LoginResponse {
    private Long id;
    private String name;

    public LoginResponse(Member member) {
        this.id = member.getId();
        this.name = member.getName();
    }
}
