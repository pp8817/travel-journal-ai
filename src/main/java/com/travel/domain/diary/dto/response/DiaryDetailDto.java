package com.travel.domain.diary.dto.response;


import com.travel.domain.diary.model.Diary;
import com.travel.domain.diary.model.Visibility;
import com.travel.domain.image.model.Image;

import java.time.LocalDate;
import java.util.List;

public record DiaryDetailDto(
        Long diaryId,
        String title,
        List<String> content,
        LocalDate travelDate,
        Visibility visibility,
        List<Image> images,
        List<String> hashtags
) {
    public static DiaryDetailDto from(Diary diary) {
        List<Image> images = null;
        if (diary.getImages() != null && !diary.getImages().isEmpty()) {
            images = diary.getImages();
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
                images,
                hashtags
        );
    }
}