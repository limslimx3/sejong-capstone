package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.*;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/api/members")
    public Long signup(@RequestBody SignupForm signupForm) {
        return memberService.signup(signupForm);
    }

    @PostMapping("/api/login/checkId")
    public ResponseEntity checkId(@RequestBody LoginValidation loginValidation) {
        return ResponseEntity.ok(memberService.loginValidation(loginValidation.getId()));
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        Member loginMember = memberService.login(loginForm);

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);

        return ResponseEntity.ok(new LoginResponse(loginMember));
    }

    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request) {
        log.info("logout");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/api/mypage")
    public ResponseEntity mypage(@SessionAttribute("loginMember") Member loginMember) {
        return ResponseEntity.ok(new MyPageResponse(loginMember));
    }
}
