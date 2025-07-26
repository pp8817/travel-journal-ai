package com.travel.domain.diary.dto.response;

public record PinResponse(
        double latitude,
        double longitude,
        String timestamp,
        String fileName
) {}