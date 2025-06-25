package com.travel.domain.diary.dto.response;

import java.time.LocalDateTime;

public record DiaryResponse(
        LocalDateTime createdAt,
        int resultCode,
        String message,
        String content,
        Long diaryId
) {}