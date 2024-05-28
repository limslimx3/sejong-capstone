package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.LoginForm;
import com.sejong.capstone.controller.dto.SignupForm;
import com.sejong.capstone.domain.Channel;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.etc.MemberRole;
import com.sejong.capstone.repository.ChannelRepository;
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
    private final ChannelRepository channelRepository;

    @Transactional
    public Member signup(SignupForm signupForm) {
        signupValidation(signupForm);
        String encodedPassword = passwordEncoder.encode(signupForm.getPassword());
        log.info("role = {}", signupForm.getMemberRole());
        Member member = Member.createMember(signupForm.getMemberId(), encodedPassword, signupForm.getName(), signupForm.getEmail(), MemberRole.valueOf(signupForm.getMemberRole()));
        memberRepository.save(member);
        createChannel(member);
        return member;
    }

    public Member login(LoginForm loginForm) {
        loginValidation(loginForm);
        return memberRepository.findByMemberId(loginForm.getLoginId()).orElseThrow();
    }

    private void loginValidation(LoginForm loginForm) {
        Member member = memberRepository.findByMemberId(loginForm.getLoginId()).orElse(null);
        if (member == null) {
            throw new IllegalStateException("해당하는 아이디가 없습니다.");
        }
        if (!passwordEncoder.matches(loginForm.getPassword(), member.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
    }

    private void signupValidation(SignupForm signupForm) {
        Member findMember = memberRepository.findByMemberId(signupForm.getMemberId()).orElse(null);
        if (findMember != null) {
            throw new IllegalStateException("이미 가입된 회원입니다.");
        }
    }

    /**
     * ID값 중복체크
     */
    public boolean loginValidation(String memberId) {
        Member member = memberRepository.findByMemberId(memberId).orElse(null);
        if (member == null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void createChannel(Member member) {
        if (member.getRole().equals(MemberRole.PROVIDER)) {
            Channel channel = Channel.createChannel("내 채널", member);
            channelRepository.save(channel);
        }
    }
}
