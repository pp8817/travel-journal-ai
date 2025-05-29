package com.travel.diary.controller;

import com.travel.diary.dto.DiaryResponse;
import com.travel.diary.service.DiaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/diary")
public class DiaryController {

    private final DiaryService diaryService;

    @Autowired
    public DiaryController(DiaryService diaryService) {
        this.diaryService = diaryService;
    }

    @PostMapping("/create")
    public DiaryResponse createDiary(@RequestParam("images") MultipartFile[] images,
                                     @RequestParam("travelDate") String travelDate,
                                     @RequestParam("travelLocation") String travelLocation,
                                     @RequestParam("feeling") String feeling,
                                     @RequestParam("companion") String companion,
                                     @RequestParam("weather") String weather) throws IOException {
        return diaryService.createDiaryWithAI(images, travelDate, travelLocation, feeling, companion, weather);
    }
}
