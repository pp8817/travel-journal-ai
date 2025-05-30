package com.travel.domain.diary.controller;

import com.travel.domain.diary.dto.DiaryDto;
import com.travel.domain.diary.service.DiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping
    public ResponseEntity<DiaryDto.DiaryResponse> createDiary(@RequestBody DiaryDto.CreateDiaryRequest request) {
        DiaryDto.DiaryResponse response = diaryService.createDiary(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
