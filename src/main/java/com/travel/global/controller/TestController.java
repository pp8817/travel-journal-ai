package com.travel.global.controller;

import com.travel.domain.image.dto.PlaceInfo;
import com.travel.global.util.GooglePlacesUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final GooglePlacesUtil googlePlacesUtil;

    @GetMapping("/geocode")
    public ResponseEntity<?> testGeocoding(
            @RequestParam("lat") double lat,
            @RequestParam("lon") double lon
    ) {
        PlaceInfo placeInfo = googlePlacesUtil.getExactPlaceInfo(lat, lon);
        return ResponseEntity.ok().body(placeInfo);
    }
}