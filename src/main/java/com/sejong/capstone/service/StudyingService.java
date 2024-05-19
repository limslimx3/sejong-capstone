package com.sejong.capstone.service;

import com.sejong.capstone.controller.dto.DictionaryDetailResponse;
import com.sejong.capstone.controller.dto.DictionaryResponse;
import com.sejong.capstone.controller.dto.MeaningCrawlingJsonResult;
import com.sejong.capstone.controller.dto.TotalCrawlingJsonResult;
import com.sejong.capstone.domain.SubtitleWord;
import com.sejong.capstone.domain.Word;
import com.sejong.capstone.repository.SubtitleWordRepository;
import com.sejong.capstone.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyingService {

    private final WebClient webClient;
    private final WordRepository wordRepository;
    private final SubtitleWordRepository subtitleWordRepository;

    /**
     * 한국어 단어로 Word 테이블을 조회하여
     *  1.존재하지 않을경우 크롤링후 DB에 저장후 반환
     *  2.존재할 경우 해당 조회값을 반환
     */
    @Transactional
    public DictionaryResponse getEngMeanings(Long subtitleWordId) throws UnsupportedEncodingException {
        //자막단어 ID 값을 통해 DB에서 단어명 조회
        SubtitleWord subtitleWord = subtitleWordRepository.findById(subtitleWordId).orElseThrow();

        //Word 테이블의 FK 값을 통해 SubtitleWord 테이블로 접근하여 간접적으로 사전 API 호출이력이 있는지 체크
        List<Word> byWordName = wordRepository.findByWordName(subtitleWord.getKorWord());

        if (byWordName.isEmpty()) { // DB에 없는 경우 크롤링
            TotalCrawlingJsonResult jsonResult = getEngMeaningsByCrawling(subtitleWord.getKorWord());
            DictionaryResponse dictionaryResponse = saveWord(jsonResult, subtitleWord);
            return dictionaryResponse;
        } else {    // DB에 있는 경우 단순조회
            List<DictionaryDetailResponse> result = wordRepository.findByWordName(subtitleWord.getKorWord()).stream()
                    .map(word -> new DictionaryDetailResponse(word.getWordName(), word.getWordMeaning()))
                    .collect(Collectors.toList());
            return new DictionaryResponse(result);
        }
    }

    /**
     * 네이버 사전 크롤링
     */
    private TotalCrawlingJsonResult getEngMeaningsByCrawling(String wordName) throws UnsupportedEncodingException {
        String encodedWord = URLEncoder.encode(wordName, "UTF-8");

        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/translate").queryParam("word", encodedWord).build())
                .retrieve()
                .bodyToMono(TotalCrawlingJsonResult.class)
                .block();
    }

    /**
     * DB Word 테이블에 저장
     */
    @Transactional
    public DictionaryResponse saveWord(TotalCrawlingJsonResult jsonResult, SubtitleWord subtitleWord) {
        List<DictionaryDetailResponse> dictionaryDetailResponses = new ArrayList<>();
        for (MeaningCrawlingJsonResult real : jsonResult.getEngMeanings()) {
            Word word = Word.createWord(real.getRealWord(), real.getMeaning(), subtitleWord);
            DictionaryDetailResponse dictionaryDetailResponse = new DictionaryDetailResponse(real.getRealWord(), real.getMeaning());
            dictionaryDetailResponses.add(dictionaryDetailResponse);
            wordRepository.save(word);
        }
        return new DictionaryResponse(dictionaryDetailResponses);
    }
}
