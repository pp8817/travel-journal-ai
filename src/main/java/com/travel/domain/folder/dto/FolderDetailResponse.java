package com.travel.domain.folder.dto;

import java.time.LocalDate;
import java.util.List;

public record FolderDetailResponse(
        String title,
        LocalDate startDate,
        LocalDate endDate,
        List<DiarySummary> diaries // 작성된 일기 요약 목록
) {
    public record DiarySummary(
            Long diaryId,
            String title,
            String content,
            LocalDate travelDate,
            String imageUrl,
            List<String> hashtags
    ) {}
}