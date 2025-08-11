package com.travel.domain.diary.util;

import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.AiDiaryResponse;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.model.Diary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DiaryMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    public static Diary toDiaryEntity(CreateDiaryRequest request, AiDiaryResponse aiDiaryResponse, List<String> savedPaths, List<String> hashtags) {
        Diary diary = Diary.builder()
                .title(aiDiaryResponse.title()) // 추후 개선
                .content(aiDiaryResponse.diary())
                .travelDate(request.date())
                .visibility(request.visibility())
                .build();
        diary.addAllTags(hashtags);
        return diary;
    }

    private static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
    public static DiaryDetailDto toDiaryDetailDto(Diary diary) {
        return DiaryDetailDto.from(diary);
    }

}