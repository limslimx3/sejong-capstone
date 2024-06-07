package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.ChannelInfoResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.MistranslationSentence;
import com.sejong.capstone.domain.MistranslationWord;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.MistranslationSentenceRepository;
import com.sejong.capstone.repository.MistranslationWordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class ChannelApiController {

    private final MemberRepository memberRepository;
    private final MistranslationSentenceRepository mistranslationSentenceRepository;
    private final MistranslationWordRepository mistranslationWordRepository;

    /**
     * 특정 사용자(영상제공자) 채널 조회
     */
    @GetMapping("/api/channel")
    public ChannelInfoResponse getChannel(@SessionAttribute("loginMember") Member loginMember) {
        Member member = memberRepository.findById(loginMember.getId()).orElseThrow();
        List<MistranslationSentence> mistranslationSentences = mistranslationSentenceRepository.findByMemberId(member.getId());
        List<MistranslationWord> mistranslationWords = mistranslationWordRepository.findByMemberId(member.getId());
        if (member.getVideos().isEmpty()) {
            return new ChannelInfoResponse(member, null, null);
        }
        return memberRepository.findMemberWithChannelById(loginMember.getId()).stream()
                .map(m -> new ChannelInfoResponse(m, mistranslationSentences, mistranslationWords))
                .findFirst()
                .orElseThrow();
    }
}
