package com.travel.global.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException ex) {
        ErrorCode code = ex.getErrorCode();
        return ResponseEntity.status(code.status())
                .body(new ErrorResponse(code.code(), code.message()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(ErrorCode.INVALID_REQUEST.status())
                .body(new ErrorResponse(ErrorCode.INVALID_REQUEST.code(), ex.getBindingResult().getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ex.printStackTrace();
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.status())
                .body(new ErrorResponse(ErrorCode.INTERNAL_SERVER_ERROR.code(), ErrorCode.INTERNAL_SERVER_ERROR.message()));
    }
}