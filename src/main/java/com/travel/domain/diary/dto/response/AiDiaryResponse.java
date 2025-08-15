package com.travel.domain.diary.dto.response;

import java.util.List;

public record AiDiaryResponse(
        String title,
        List<String> content
) {}