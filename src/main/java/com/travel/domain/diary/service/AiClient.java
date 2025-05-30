package com.travel.domain.diary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.travel.domain.diary.dto.DiaryDto.AiDiaryRequest;
import static com.travel.domain.diary.dto.DiaryDto.AiDiaryResponse;

@Component
@RequiredArgsConstructor
public class AiClient {

    private final WebClient webClient;

    @Value("${ai.server.url}")
    private String aiServerUrl;

    public AiDiaryResponse generate(AiDiaryRequest request) {
        return webClient
                .post()
                .uri(aiServerUrl)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AiDiaryResponse.class)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("AI 서버 응답이 null입니다."));
    }
}