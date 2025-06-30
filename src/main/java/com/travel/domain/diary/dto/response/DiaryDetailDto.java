package com.travel.domain.diary.dto.response;


import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.DiaryEmotion;
import com.travel.domain.diary.model.Visibility;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record DiaryDetailDto(
        Long diaryId,
        String title,
        String content,
        String location,
        String weather,
        String companion,
        LocalDate travelDate,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Visibility visibility,
        String imageUrl,
        List<DiaryEmotion> emotions
) {
    public static DiaryDetailDto from(Diary diary) {
        String imageUrl = null;
        if (diary.getImageUrl() != null && !diary.getImageUrl().isEmpty()) {
            imageUrl = diary.getImageUrl();  // 첫 번째 이미지 URL 사용
        }

        return new DiaryDetailDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getLocation(),
                diary.getWeather(),
                diary.getCompanion(),
                diary.getTravelDate(),
                diary.getCreatedAt(),
                diary.getUpdatedAt(),
                diary.getVisibility(),
                imageUrl,
                diary.getDiaryEmotions()
        );
    }
}