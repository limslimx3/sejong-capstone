package com.sejong.capstone.controller.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

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
}
