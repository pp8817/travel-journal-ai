package com.travel.domain.diary.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
            Visibility visibility,
            String image
    ) {
    }

    @Builder
    public record AiDiaryRequest(
            @JsonProperty("date")
            String date,

            @JsonProperty("location")
            String location,

            @JsonProperty("emotions")
            List<String> emotions,

            @JsonProperty("weather")
            String weather,

            @JsonProperty("companion")
            String companion,

            @JsonProperty("image")
            String image
    ) {}

    /**
     * Response DTO
     */
    public record AiDiaryResponse(
            String diary
    ) {}

    public record DiaryResponse(
            LocalDateTime createdAt,
            int resultCode,
            String message,
            String content,
            Long diaryId
    ) {}
}
