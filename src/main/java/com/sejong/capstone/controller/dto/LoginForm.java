package com.sejong.capstone.controller.dto;

import lombok.Data;

/**
 * 로그인시 입력한 데이터 담기 위한 DTO
 */
@Data
public class LoginForm {
    private String loginId;
    private String password;
}
