package com.travel.domain.diary.dto.response;

import com.travel.domain.diary.model.Diary;
import com.travel.domain.image.model.Image;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Builder
public class DiaryListDto {

    private Long id;
    private String content;
    private Image image;
    private List<String> hashtags;
    private LocalDate travelDate;

    public static DiaryListDto from(Diary diary) {

        return DiaryListDto.builder()
                .id(diary.getId())
                .content(diary.getContent().get(0))
                .image(diary.getImages().get(0))
                .hashtags(diary.getHashtags())
                .travelDate(diary.getTravelDate())
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