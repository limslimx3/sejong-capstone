package com.sejong.capstone.domain;

import jakarta.persistence.Enumerated;

/**
 * ADMIN: 관리자
 * PROVIDER: 영상 제공자
 * USER: 영상 학습 이용자
 */
public enum MemberRole {
    ADMIN, PROVIDER, USER
}
