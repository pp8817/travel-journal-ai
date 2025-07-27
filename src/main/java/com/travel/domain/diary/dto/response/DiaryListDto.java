package com.travel.domain.diary.dto.response;

import com.travel.domain.diary.model.Diary;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public class DiaryListDto {

    private Long id;              // 다이어리 식별자
    private String content;         // 제목
    private List<String> imgUrl;        // 대표 이미지
    private LocalDate travelDate; // 여행 날짜
    private String location;      // 여행지
    private String weather;       // 날씨 (선택사항)

    public static DiaryListDto from(Diary diary) {

        return DiaryListDto.builder()
                .id(diary.getId())
                .content(getFirstSentence(diary.getContent()))
                .imgUrl(diary.getImagePaths())
                .travelDate(diary.getTravelDate())
                .location(diary.getLocation())
                .build();
    }

    private static String getFirstSentence(String text) {
        if (text == null || text.isEmpty()) {
            return "";
        }

        int period = text.indexOf(".");
        int exclamation = text.indexOf("!");
        int question = text.indexOf("?");

        // 음수는 무시하고 최소 인덱스 찾기
        int endIndex = Integer.MAX_VALUE;
        if (period != -1) endIndex = Math.min(endIndex, period);
        if (exclamation != -1) endIndex = Math.min(endIndex, exclamation);
        if (question != -1) endIndex = Math.min(endIndex, question);

        if (endIndex == Integer.MAX_VALUE) {
            return text; // 마침표가 없으면 전체 문장을 반환
        }

        return text.substring(0, endIndex + 1).trim();
    }
}