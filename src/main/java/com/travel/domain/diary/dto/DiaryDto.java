package com.travel.domain.diary.dto;

import com.travel.domain.diary.model.Visibility;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DiaryDto {
    /**
     * Request DTO
     */
    public record CreateDiaryRequest(
            LocalDate date,
            String location,
            String weather,
            String companion,
            List<String> emotions,
            Visibility visibility
    ) {
    }

    @Builder
    public record AiDiaryRequest(
            String date,
            String location,
            List<String> emotions,
            String weather,
            String companion
    ) {}

    /**
     * Response DTO
     */
    public record AiDiaryResponse(
            String content
    ) {}

    public record DiaryResponse(
            LocalDateTime createdAt,
            int resultCode,
            String message,
            String content,
            Long diaryId
    ) {}
}
