package com.travel.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // AI 관련
    AI_REQUEST_FAILED("AI01", "AI 요청에 실패했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    AI_RESPONSE_NULL("AI02", "AI 응답이 null입니다.", HttpStatus.INTERNAL_SERVER_ERROR),

    // Diary 관련
    DIARY_CREATION_FAILED("D01", "일기 생성 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    DIARY_NOT_FOUND("D02", "요청한 일기를 찾을 수 없습니다.", HttpStatus.NOT_FOUND),

    // 공통
    INVALID_REQUEST("C01", "잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("C99", "알 수 없는 서버 오류입니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus status;

    ErrorCode(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }

    public HttpStatus status() {
        return status;
    }
}