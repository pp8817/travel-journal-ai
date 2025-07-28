package com.travel.domain.diary.controller;

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

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<DiaryResponse> createDiary(
            @RequestPart("data") CreateDiaryRequest data,
            @RequestPart("images") List<MultipartFile> images
    ) {
        DiaryResponse response = diaryService.createDiary(data, images);
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
