package com.sejong.capstone.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sejong.capstone.controller.dto.DictionaryDetailResponse;
import com.sejong.capstone.controller.dto.DictionaryResponse;
import com.sejong.capstone.controller.dto.MeaningCrawlingJsonResult;
import com.sejong.capstone.controller.dto.TotalDictionaryJsonResult;
import com.sejong.capstone.domain.SubtitleWord;
import com.sejong.capstone.domain.Word;
import com.sejong.capstone.repository.SubtitleWordRepository;
import com.sejong.capstone.repository.WordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class StudyingService {

    @Value("${naver.api.client.id}")
    private String clientId;

    @Value("${naver.api.client.secret}")
    private String clientSecret;

    private final WebClient webClientForPapagoAPI;
    private final WebClient webClientForDictionaryAPI;
    private final ObjectMapper objectMapper;
    private final WordRepository wordRepository;
    private final SubtitleWordRepository subtitleWordRepository;

//    /**
//     * 한국어 단어로 Word 테이블을 조회하여
//     *  1.존재하지 않을경우 크롤링후 DB에 저장후 반환
//     *  2.존재할 경우 해당 조회값을 반환
//     */
//    @Transactional
//    public DictionaryResponse getEngMeanings(Long subtitleWordId) throws UnsupportedEncodingException {
//        //자막단어 ID 값을 통해 DB에서 단어명 조회
//        SubtitleWord subtitleWord = subtitleWordRepository.findById(subtitleWordId).orElseThrow();
//
//        //Word 테이블의 FK 값을 통해 SubtitleWord 테이블로 접근하여 간접적으로 사전 API 호출이력이 있는지 체크
//        List<Word> byWordName = wordRepository.findByWordName(subtitleWord.getKorWord());
//
//        if (byWordName.isEmpty()) { // DB에 없는 경우 크롤링
//            TotalCrawlingJsonResult jsonResult = getEngMeaningsByCrawling(subtitleWord.getKorWord());
//            DictionaryResponse dictionaryResponse = saveWord(jsonResult, subtitleWord);
//            return dictionaryResponse;
//        } else {    // DB에 있는 경우 단순조회
//            List<DictionaryDetailResponse> result = wordRepository.findByWordName(subtitleWord.getKorWord()).stream()
//                    .map(word -> new DictionaryDetailResponse(word.getWordName(), word.getWordMeaning()))
//                    .collect(Collectors.toList());
//            return new DictionaryResponse(result);
//        }
//    }
//
//    /**
//     * 네이버 사전 크롤링
//     */
//    private TotalCrawlingJsonResult getEngMeaningsByCrawling(String wordName) throws UnsupportedEncodingException {
//        String encodedWord = URLEncoder.encode(wordName, "UTF-8");
//
//        return webClient.get()
//                .uri(uriBuilder -> uriBuilder.path("/translate").queryParam("word", encodedWord).build())
//                .retrieve()
//                .bodyToMono(TotalCrawlingJsonResult.class)
//                .block();
//    }

    /**
     * 1차로 Papago API 이용하여 한국어 단어 -> 영어 단어로 번역
     * 2차로 Dictionary API 이용하여 띄어쓰기 없는 영어 단어 대상으로 상세한 사전 뜻 반환
     */
    @Transactional
    public DictionaryResponse getEngMeanings(Long subtitleWordId) throws JsonProcessingException {
        TotalDictionaryJsonResult totalDictionaryJsonResult = null;
        SubtitleWord subtitleWord = subtitleWordRepository.findById(subtitleWordId).orElseThrow();
        log.info("subtitleWord: {}", subtitleWord);

        String papagoResponse = webClientForPapagoAPI.post()
                .uri("/nmt/v1/translation")
                .header("X-NCP-APIGW-API-KEY-ID", clientId)
                .header("X-NCP-APIGW-API-KEY", clientSecret)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildRequestBody("ko", "en", subtitleWord.getKorWord()))
                .retrieve()
                .bodyToMono(String.class)
                .block();
        log.info("block: {}", papagoResponse);

        JsonNode papagoJson = objectMapper.readTree(papagoResponse);
        String text = papagoJson.path("message").path("result").path("translatedText").asText();
        if (StringUtils.containsWhitespace(text)) {  //공백이 포함되서 그대로 보여줄 경우
            log.info("text with blank: {}", text);

            List<MeaningCrawlingJsonResult> list = new ArrayList<>();
            list.add(new MeaningCrawlingJsonResult(subtitleWord.getKorWord(), text));
            totalDictionaryJsonResult = new TotalDictionaryJsonResult(subtitleWord.getKorWord(), list);
        } else {    //공백이 없어 영영사전 API 결과를 보여줄 경우
            log.info("text without blank: {}", text);

            String dictionaryResponse = webClientForDictionaryAPI.get()
                    .uri(uriBuilder -> uriBuilder.path("/api/v3/references/collegiate/json/" + text).queryParam("key", "07deffac-df36-4825-8b8d-2793bf701080").build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            JsonNode dictionaryJson = objectMapper.readTree(dictionaryResponse);
            List<MeaningCrawlingJsonResult> dictionaryResult = extractDtValues(dictionaryJson).stream()
                    .map(translatedText -> new MeaningCrawlingJsonResult(subtitleWord.getKorWord(), translatedText))
                    .collect(Collectors.toList());

            if(dictionaryResult.isEmpty()) {    //사전 API 결과가 없을 경우
                dictionaryResult.add(new MeaningCrawlingJsonResult(subtitleWord.getKorWord(), text));
            }

            totalDictionaryJsonResult = new TotalDictionaryJsonResult(subtitleWord.getKorWord(), dictionaryResult);
        }
        DictionaryResponse dictionaryResponse = saveWord(totalDictionaryJsonResult, subtitleWord);
        return dictionaryResponse;
    }

    private String buildRequestBody(String sourceLang, String targetLang, String text) {
        return String.format("{\"source\":\"%s\",\"target\":\"%s\",\"text\":\"%s\"}", sourceLang, targetLang, text);
    }

    /**
     * 영영사전 API 결과값에서 영어로 번역된 의미 파트만 추출1
     */
    private List<String> extractDtValues(JsonNode rootNode) {
        List<String> dtValues = new ArrayList<>();
        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                extractDtFromNode(node, dtValues);
            }
        }
        return dtValues;
    }

    /**
     * 영영사전 API 결과값에서 영어로 번역된 의미 파트만 추출2
     */
    private void extractDtFromNode(JsonNode node, List<String> dtValues) {
        if (node.isObject()) {
            Iterator<String> fieldNames = node.fieldNames();
            while (fieldNames.hasNext()) {
                String fieldName = fieldNames.next();
                JsonNode childNode = node.get(fieldName);
                if (fieldName.equals("dt") && childNode.isArray()) {
                    for (JsonNode dtNode : childNode) {
                        if (dtNode.isArray() && dtNode.size() > 1) {
                            JsonNode textNode = dtNode.get(1);
                            if (textNode.isTextual()) {
                                dtValues.add(textNode.asText().replace("{bc}", ""));
                            }
                        }
                    }
                } else {
                    extractDtFromNode(childNode, dtValues);
                }
            }
        } else if (node.isArray()) {
            for (JsonNode childNode : node) {
                extractDtFromNode(childNode, dtValues);
            }
        }
    }

    /**
     * DB Word 테이블에 저장
     */
    @Transactional
    public DictionaryResponse saveWord(TotalDictionaryJsonResult jsonResult, SubtitleWord subtitleWord) {
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
