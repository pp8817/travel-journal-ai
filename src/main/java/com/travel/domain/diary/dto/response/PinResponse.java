package com.travel.domain.diary.dto.response;

import java.time.LocalDateTime;

public record PinResponse(
        Double latitude,
        Double longitude,
        LocalDateTime timestamp,
        String fileName
) {}