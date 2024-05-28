package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.ChannelInfoResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.repository.ChannelRepository;
import com.sejong.capstone.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class ChannelApiController {

    private final MemberRepository memberRepository;

    /**
     * 특정 사용자(영상제공자) 채널 조회
     */
    @GetMapping("/api/channel")
    public ChannelInfoResponse getChannel(@SessionAttribute("loginMember") Member loginMember) {
        return memberRepository.findMemberWithChannelById(loginMember.getId()).stream()
                .map(member -> new ChannelInfoResponse(member))
                .findFirst()
                .orElseThrow();
    }
}
