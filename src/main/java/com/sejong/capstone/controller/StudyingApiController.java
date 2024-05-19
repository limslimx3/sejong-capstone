package com.sejong.capstone.controller;

import com.sejong.capstone.controller.dto.DictionaryResponse;
import com.sejong.capstone.controller.dto.TotalCrawlingJsonResult;
import com.sejong.capstone.service.StudyingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;


@RequiredArgsConstructor
@RestController
public class StudyingApiController {

    private final StudyingService studyingService;

    @GetMapping("/api/dictionary")
    public ResponseEntity getDictionary(@RequestParam Long subtitleWordId) throws UnsupportedEncodingException {
        DictionaryResponse result = studyingService.getEngMeanings(subtitleWordId);
        return ResponseEntity.ok(result);
    }
}
