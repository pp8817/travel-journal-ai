package com.travel.domain.diary.dto.response;

import com.travel.domain.diary.model.Diary;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public class DiaryListDto {

    private Long id;              // 다이어리 식별자
    private String title;         // 제목
    private String imgUrl;        // 대표 이미지
    private LocalDate travelDate; // 여행 날짜
    private String location;      // 여행지
    private String weather;       // 날씨 (선택사항)

    public static DiaryListDto from(Diary diary) {
        return DiaryListDto.builder()
                .id(diary.getId())
                .title(diary.getTitle())
                .imgUrl(diary.getImageUrl())
                .travelDate(diary.getTravelDate())
                .location(diary.getLocation())
                .weather(diary.getWeather()) // 필요 시 포함
                .build();
    }
}