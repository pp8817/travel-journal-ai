package com.travel.global.controller;

import com.travel.domain.diary.dto.response.PinResponse;
import com.travel.domain.image.dto.ImageMetaData;
import com.travel.domain.image.dto.PlaceInfo;
import com.travel.global.util.GooglePlacesUtil;
import com.travel.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class TestController {

    private final GooglePlacesUtil googlePlacesUtil;
    private final ImageUtil imageUtil;

    @GetMapping("/geocode")
    public ResponseEntity<?> testGeocoding(
            List<MultipartFile> images
    ) {
        List<ImageMetaData> rawPins = imageUtil.extractMetadata(images);
        List<PinResponse> response = rawPins.stream()
                .sorted(Comparator.comparing(ImageMetaData::timestamp))
                .map(meta -> {
                    PlaceInfo placeInfo = googlePlacesUtil.getExactPlaceInfo(meta.latitude(), meta.longitude());

                    return new PinResponse(
                            meta.latitude(),
                            meta.longitude(),
                            meta.timestamp(),
                            meta.fileName(),
                            placeInfo.name(),
                            placeInfo.vicinity()
                    );
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(response);
    }
}