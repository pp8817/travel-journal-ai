package com.travel.domain.diary.service;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.global.exception.CustomException;
import com.travel.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
@Slf4j
public class AiClient {

    private final WebClient webClient;

    public AiDiaryResponse generate(AiDiaryRequest request) {
        return webClient
                .post()
                .uri("/short_diary/generate")
                .bodyValue(request)
                .retrieve()
                .onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        response -> response.bodyToMono(String.class).flatMap(body -> {
                            log.error("❌ AI 서버 에러 발생 - 상태 코드: {}, 응답 바디: {}",
                                    response.statusCode().value(), body);
                            return Mono.error(new CustomException(ErrorCode.AI_REQUEST_FAILED));
                        })
                )
                .bodyToMono(AiDiaryResponse.class)
                .blockOptional()
                .orElseThrow(() -> new CustomException(ErrorCode.AI_RESPONSE_NULL));
    }
}