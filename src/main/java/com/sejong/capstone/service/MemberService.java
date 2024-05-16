package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.LoginForm;
import com.sejong.capstone.controller.dto.SignupForm;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.etc.MemberRole;
import com.sejong.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Long signup(SignupForm signupForm) {
        signupValidation(signupForm);
        String encodedPassword = passwordEncoder.encode(signupForm.getPassword());
        Member member = Member.createMember(signupForm.getMemberId(), encodedPassword, signupForm.getName(), signupForm.getEmail(), MemberRole.valueOf(signupForm.getMemberRole()));
        memberRepository.save(member);
        return member.getId();
    }

    public Member login(LoginForm loginForm) {
        loginValidation(loginForm);
        return memberRepository.findByMemberId(loginForm.getLoginId());
    }

    private void loginValidation(LoginForm loginForm) {
        Member member = memberRepository.findByMemberId(loginForm.getLoginId());
        if (member == null) {
            throw new IllegalStateException("해당하는 아이디가 없습니다.");
        }
        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void signupValidation(SignupForm signupForm) {
        Member findMember = memberRepository.findByMemberId(signupForm.getMemberId());
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }
}
