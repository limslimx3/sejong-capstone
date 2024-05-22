package com.sejong.capstone.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebFluxConfig {

    @Bean
    public WebClient webClientForFastAPI() {
        return WebClient.builder()
                .baseUrl("http://101.235.73.77:8000")
                .build();
    }

    @Bean
    public WebClient webClientForPapagoAPI() {
        return WebClient.builder()
                .baseUrl("https://naveropenapi.apigw.ntruss.com")
                .build();
    }

    @Bean
    public WebClient webClientForDictionaryAPI() {
        return WebClient.builder()
                .baseUrl("https://www.dictionaryapi.com")
                .build();
    }
}
