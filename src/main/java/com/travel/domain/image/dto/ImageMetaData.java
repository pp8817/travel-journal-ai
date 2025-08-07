package com.travel.domain.image.dto;

import java.time.LocalDateTime;

public record ImageMetaData(
        Double latitude,
        Double longitude,
        LocalDateTime timestamp,
        String fileName
) {}