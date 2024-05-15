package com.sejong.capstone.controller.dto;

import com.sejong.capstone.domain.etc.MemberRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberForm {
    private String memberId;
    private String mail;
    private String name;
    private String password;
    private MemberRole role;
}
