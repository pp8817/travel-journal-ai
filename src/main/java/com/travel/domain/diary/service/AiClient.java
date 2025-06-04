package com.travel.domain.diary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.travel.domain.diary.dto.DiaryDto.AiDiaryRequest;
import static com.travel.domain.diary.dto.DiaryDto.AiDiaryResponse;

@Component
@RequiredArgsConstructor
public class AiClient {

    private final WebClient webClient;

    public AiDiaryResponse generate(AiDiaryRequest request) {
        System.out.println("📦 AI 전송 JSON: " + request);

        return webClient
                .post()
                .uri("/diary/generate")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).map(body -> {
                            System.err.println("❌ AI 서버 응답 에러: " + body);
                            throw new RuntimeException("AI 서버 오류: " + body);
                        })
                )
                .bodyToMono(AiDiaryResponse.class)
                .blockOptional()
                .orElseThrow(() -> new RuntimeException("AI 서버 응답이 null입니다."));
    }
}