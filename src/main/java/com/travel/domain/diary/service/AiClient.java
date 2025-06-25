package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.global.exception.CustomException;
import com.travel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class AiClient {

    private final WebClient webClient;

    public AiDiaryResponse generate(AiDiaryRequest request) {
        return webClient
                .post()
                .uri("/diary/generate")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).map(body -> {
                            throw new CustomException(ErrorCode.AI_REQUEST_FAILED);
                        })
                )
                .bodyToMono(AiDiaryResponse.class)
                .blockOptional()
                .orElseThrow(() -> new CustomException(ErrorCode.AI_RESPONSE_NULL));
    }
}