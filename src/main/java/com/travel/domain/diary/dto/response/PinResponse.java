package com.travel.domain.diary.dto.response;

import java.time.LocalDateTime;

public record PinResponse(
        double latitude,
        double longitude,
        LocalDateTime timestamp,
        String fileName
) {}