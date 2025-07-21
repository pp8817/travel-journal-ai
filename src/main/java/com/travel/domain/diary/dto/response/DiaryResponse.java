package com.travel.domain.diary.dto.response;

import java.util.List;

public record DiaryResponse(
        Long diaryId,
        List<PinResponse> pins
) {}