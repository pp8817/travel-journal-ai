package com.travel.domain.diary.util;

import com.travel.domain.diary.dto.request.AiDiaryRequest;
import com.travel.domain.diary.dto.request.CreateDiaryRequest;
import com.travel.domain.diary.dto.response.DiaryDetailDto;
import com.travel.domain.diary.dto.response.DiaryResponse;
import com.travel.domain.diary.dto.response.PinResponse;
import com.travel.domain.diary.model.Diary;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

public class DiaryMapper {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");

    public static AiDiaryRequest toAiDiaryRequest(CreateDiaryRequest request) {
        return AiDiaryRequest.builder()
                .date(formatDate(request.date()))
                .emotions(request.emotions())
                .images(request.images())
                .build();
    }

    public static Diary toDiaryEntity(CreateDiaryRequest request, String content) {
        Diary diary = Diary.builder()
                .title("제목 없음") // 추후 개선
                .content(content)
                .travelDate(request.date())
                .visibility(request.visibility())
                .build();
        diary.addAllImage(request.images());
        return diary;
    }
    public static DiaryResponse toDiaryResponse(Diary diary, List<PinResponse> pins) {
        return new DiaryResponse(diary.getId(), pins);
    }

    private static String formatDate(LocalDate date) {
        return date.format(FORMATTER);
    }
    public static DiaryDetailDto toDiaryDetailDto(Diary diary) {
        return DiaryDetailDto.from(diary);
    }

}