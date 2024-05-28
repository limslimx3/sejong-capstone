package com.sejong.capstone.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sejong.capstone.controller.dto.DictionaryResponse;
import com.sejong.capstone.controller.dto.MistranslationWordReportRequest;
import com.sejong.capstone.controller.dto.NoteInnerResponse;
import com.sejong.capstone.controller.dto.NoteTotalResponse;
import com.sejong.capstone.domain.Member;
import com.sejong.capstone.domain.Note;
import com.sejong.capstone.repository.NoteRepository;
import com.sejong.capstone.repository.SubtitleWordRepository;
import com.sejong.capstone.service.StudyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
public class StudyingApiController {

    private final StudyingService studyingService;
    private final NoteRepository noteRepository;

    /**
     * 사전 API 기능 처리
     */
    @GetMapping("/api/dictionary")
    public ResponseEntity getDictionary(@RequestParam Long subtitleWordId) throws JsonProcessingException {
        DictionaryResponse result = studyingService.getEngMeanings(subtitleWordId);
        return ResponseEntity.ok(result);
    }

    /**
     * 단어장 목록 조회
     */
    @GetMapping("/api/note")
    public ResponseEntity getNoteList(@SessionAttribute(name = "loginMember") Member loginMember) {
        List<NoteInnerResponse> resultList = noteRepository.findAllNoteSubtitleVideoByMemberId(loginMember.getId()).stream()
                .map(note -> new NoteInnerResponse(note))
                .toList();
        return ResponseEntity.ok(new NoteTotalResponse(resultList));
    }

    /**
     * 단어장에 단어 추가
     */
    @PostMapping("/api/note/{id}")
    public ResponseEntity addNote(@SessionAttribute(name = "loginMember") Member loginMember, @PathVariable("id") Long id, @RequestParam boolean isCorrected) {
        studyingService.addWordToNote(loginMember.getId(), id, isCorrected);
        return ResponseEntity.ok().build();
    }

    /**
     * 단어장에서 단어 삭제
     */
    @DeleteMapping("/api/note/{noteId}")
    public ResponseEntity deleteNote(@SessionAttribute(name = "loginMember") Member loginMember, @PathVariable("noteId") Long noteId) {
        Note note = noteRepository.findById(noteId).orElseThrow();
        if (!note.getMember().getId().equals(loginMember.getId())) {
            throw new IllegalStateException("해당 노트에 대한 권한이 없습니다.");
        }
        noteRepository.delete(note);
        return ResponseEntity.ok().build();
    }

    /**
     * 단어 오역 신고 기능
     */
    @GetMapping("/api/report/word/{subtitleWordId}")
    public ResponseEntity reportWord(@PathVariable("subtitleWordId") Long subtitleWordId) {
        studyingService.reportWord(subtitleWordId);
        return ResponseEntity.ok().build();
    }

    /**
     * 단어 오역 수정 기능
     */
    @PostMapping("/api/report/word")
    public ResponseEntity correctWord(@RequestBody MistranslationWordReportRequest request) {
        studyingService.correctWord(request);
        return ResponseEntity.ok().build();
    }
}
