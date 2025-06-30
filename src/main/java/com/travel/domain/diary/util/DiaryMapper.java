package com.travel.domain.diary.util;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.model.Diary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DiaryMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    public static AiDiaryRequest toAiDiaryRequest(CreateDiaryRequest request) {
        return AiDiaryRequest.builder()
                .date(formatDate(request.date()))
                .location(request.location())
                .emotions(request.emotions())
                .weather(request.weather())
                .companion(request.companion())
                .image(request.image())
                .build();
    }

    public static Diary toDiaryEntity(CreateDiaryRequest request, String content) {
        return Diary.builder()
                .title("제목 없음") // 추후 개선
                .content(content)
                .travelDate(request.date())
                .location(request.location())
                .weather(request.weather())
                .companion(request.companion())
                .visibility(request.visibility())
                .imgUrl(request.image())
                .build();
    }

    private static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
    public static DiaryDetailDto toDiaryDetailDto(Diary diary) {
        return DiaryDetailDto.from(diary);
    }

}