package com.sejong.capstone.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 회원가입시 입력한 데이터 담기 위한 DTO
 */
@Data
public class SignupForm {

    @NotNull
    private String memberId;

    @NotNull
    private String password;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String memberRole;
}
