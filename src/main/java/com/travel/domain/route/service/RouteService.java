package com.travel.domain.route.service;

import com.travel.domain.route.dto.request.RouteRequest;
import com.travel.domain.route.dto.response.RouteResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@Transactional(readOnly = true)
public class RouteService {

    private final WebClient webClient;

    public RouteService() {
        this.webClient = WebClient.builder()
                .baseUrl("http://") //  AI 서버 주소
                .build();
    }

    @Transactional
    public RouteResponse createRoute(RouteRequest request) {
        return webClient.post()
                .uri("/route") // AI 서버 엔드포인트
                .bodyValue(request)
                .retrieve()
                .bodyToMono(RouteResponse.class)
                .block();
    }
}
