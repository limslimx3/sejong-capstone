package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.LoginForm;
import com.sejong.capstone.controller.dto.SignupForm;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/api/members")
    public Long signup(@RequestBody SignupForm signupForm) {
        return memberService.signup(signupForm);
    }

    @PostMapping("/api/login")
    public ResponseEntity login(@RequestBody LoginForm loginForm, HttpServletRequest request) {
        Member loginMember = memberService.login(loginForm);

        HttpSession session = request.getSession();
        session.setAttribute("loginMember", loginMember);
        return ResponseEntity.ok(loginMember.getId());
    }
}
