package com.travel.global.exception;

public record ErrorResponse(
        String code,
        String message
) {}