package com.travel.global.controller;

import com.travel.global.util.GeocodingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final GeocodingUtil geocodingUtil;

    @GetMapping("/geocode")
    public ResponseEntity<?> testGeocoding(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon
    ) {
        String location = geocodingUtil.getLocation(lat, lon);
        return ResponseEntity.ok().body(new GeocodeResponse(location));
    }

    // 내부 Response DTO
    private record GeocodeResponse(String location) {}
}