package com.travel.domain.route.controller;

import com.travel.domain.route.dto.request.RouteRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/recommendations")
public class RouteController {

    @PostMapping("/routes")
    public ResponseEntity<String> receiveRouteRequest(
            @RequestParam("date") String date,
            @RequestParam("transport") String transport,
            @RequestParam("latitude") Double latitude,
            @RequestParam("longitude") Double longitude,
            @RequestParam(value = "images", required = false) List<MultipartFile> images
    ) {
        RouteRequest request = new RouteRequest();
        request.setDate(LocalDate.parse(date));
        request.setTransport(transport);
        request.setLatitude(latitude);
        request.setLongitude(longitude);
        request.setImages(images);

        System.out.println("받은 요청: " + request);

        return ResponseEntity.ok("요청이 정상 접수");
    }
}
