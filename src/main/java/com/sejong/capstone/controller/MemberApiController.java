package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.MemberForm;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class MemberApiController {

    private final MemberRepository memberRepository;

    @PostMapping("/api/member")
    public void signup(@RequestBody MemberForm memberForm) {
        Member member = Member.createMember(memberForm);
        memberRepository.save(member);
    }
}
