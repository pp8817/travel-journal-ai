package com.example.demo.controller;

import com.example.demo.dto.DiaryCreateRequest;
import com.example.demo.dto.DiaryResponse;
import com.example.demo.service.DiaryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api/diaries")
public class DiaryController {

    private final DiaryService diaryService;

    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<DiaryResponse> createDiary(
            @RequestPart("data") @Valid DiaryCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images) {

        DiaryResponse response = diaryService.createDiary(request, images);
        return ResponseEntity.ok(response);
    }
}