package com.travel.domain.image.dto;

import java.time.LocalDateTime;

public record ImageDto(
        Double latitude,
        Double longitude,
        LocalDateTime timestamp,
        String fileName,
        String location,
        String vicinity,
        String fileUrl
) {}