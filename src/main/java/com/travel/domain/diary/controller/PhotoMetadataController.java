package com.travel.domain.diary.controller;

import com.travel.domain.diary.dto.response.PinResponse;
import com.travel.domain.diary.service.PhotoMetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary/photos")
public class PhotoMetadataController {

    private final PhotoMetadataService photoMetadataService;

    @PostMapping("/pins")
    public ResponseEntity<List<PinResponse>> extractPins(@RequestPart("images") List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            List<PinResponse> pins = photoMetadataService.extractPins(images);
            return ResponseEntity.ok(pins);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
