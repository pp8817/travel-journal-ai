package com.travel.domain.diary.dto.response;


import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Visibility;

import java.time.LocalDate;
import java.util.List;

public record DiaryDetailDto(
        Long diaryId,
        String title,
        String content,
        LocalDate travelDate,
        Visibility visibility,
        List<String> hashtags,
        List<String> imageUrl
) {
    public static DiaryDetailDto from(Diary diary) {
        List<String> imageUrl = null;
        if (diary.getImagePaths() != null && !diary.getImagePaths().isEmpty()) {
            imageUrl = diary.getImagePaths();  // 첫 번째 이미지 URL 사용
        }

        List<String> hashtags = null;
        if (diary.getHashtags() != null && !diary.getHashtags().isEmpty()) {
            hashtags = diary.getHashtags();  // 첫 번째 이미지 URL 사용
        }

        return new DiaryDetailDto(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.getTravelDate(),
                diary.getVisibility(),
                imageUrl,
                hashtags
        );
    }
}