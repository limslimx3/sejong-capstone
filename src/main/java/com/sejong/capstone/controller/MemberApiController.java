package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.*;
import com.sejong.capstone.domain.Channel;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.etc.MemberRole;
import com.sejong.capstone.repository.ChannelRepository;
import com.sejong.capstone.repository.MemberRepository;
import com.sejong.capstone.repository.MistranslationSentenceRepository;
import com.sejong.capstone.repository.MistranslationWordRepository;
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
    private final MistranslationWordRepository mistranslationWordRepository;
    private final MistranslationSentenceRepository mistranslationSentenceRepository;

    @PostMapping("/api/members")

    public Long signup(@RequestBody SignupForm signupForm) {
        Member member = memberService.signup(signupForm);
        return member.getId();
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

        // 제공자의 경우 신고된 단어중 아직 수정되지 않은 단어가 있다면 알림
        if(loginMember.getRole().equals(MemberRole.PROVIDER)) {
            boolean isWordReportExist = mistranslationWordRepository.findAllFetchJoin().stream()
                    .anyMatch(mistranslationWord -> (mistranslationWord.getSubtitleWord().getSubtitleSentence().getVideo().getMember().getId().equals(loginMember.getId())) && (!mistranslationWord.isCorrected()));
            boolean isSentenceReportExist = mistranslationSentenceRepository.findAllFetchJoin().stream()
                    .anyMatch(mistranslationSentence -> (mistranslationSentence.getSubtitleSentence().getVideo().getMember().getId().equals(loginMember.getId())) && (!mistranslationSentence.isCorrected()));

            if(isWordReportExist || isSentenceReportExist) {
                return ResponseEntity.ok(new LoginResponse(loginMember, true));
            }
        }

        return ResponseEntity.ok(new LoginResponse(loginMember, false));
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
