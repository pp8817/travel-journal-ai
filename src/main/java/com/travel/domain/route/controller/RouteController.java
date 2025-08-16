package com.travel.domain.route.controller;

import com.travel.domain.route.dto.request.RouteRequest;
import com.travel.domain.route.dto.response.RouteResponse;
import com.travel.domain.route.service.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/routes") // 변경예정
public class RouteController {

    private final RouteService routeService;

    @PostMapping
    public ResponseEntity<RouteResponse> createRoute(@RequestBody RouteRequest request) {
        RouteResponse response = routeService.createRoute(request);
        return ResponseEntity.ok(response);
    }
}
