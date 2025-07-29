package com.travel.domain.diary.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.dto.response.DiaryListDto;
import com.travel.domain.diary.dto.response.DiaryResponse;
import com.travel.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/diary")
public class DiaryController {

    private final DiaryService diaryService;
    private final ObjectMapper objectMapper;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryResponse> createDiary(
            @RequestPart("data") String data,
            @RequestPart("images") List<MultipartFile> images
    ) throws JsonProcessingException {
        CreateDiaryRequest request = objectMapper.readValue(data, CreateDiaryRequest.class);
        DiaryResponse response = diaryService.createDiary(request, images);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DiaryDetailDto> getDiary(@PathVariable Long id) {
        DiaryDetailDto response = diaryService.getDiaryById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/list")
    public ResponseEntity<List<DiaryListDto>> diaryList() {
        List<DiaryListDto> response = diaryService.getDiaryListByPublic();
        return ResponseEntity.ok(response);
    }
}
