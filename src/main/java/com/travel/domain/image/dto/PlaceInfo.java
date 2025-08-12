package com.travel.domain.image.dto;

public record PlaceInfo(
        String name,
        String vicinity,
        double rating,
        int userRatingsTotal
) {}