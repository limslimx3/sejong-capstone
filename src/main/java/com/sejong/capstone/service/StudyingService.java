package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.TotalCrawlingJsonResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyingService {

    private final WebClient webClient;

    public TotalCrawlingJsonResult getEngMeaningsByCrawling(String word) throws UnsupportedEncodingException {

        String encodedWord = URLEncoder.encode(word, "UTF-8");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/translate").queryParam("word", encodedWord).build())
                .retrieve()
                .bodyToMono(TotalCrawlingJsonResult.class)
                .block();
    }
}
