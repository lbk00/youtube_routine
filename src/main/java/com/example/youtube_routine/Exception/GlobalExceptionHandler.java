package com.example.youtube_routine.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ 존재하지 않는 사용자 (404 Not Found)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body((ErrorResponse) new DefaultErrorResponse("User not found", e.getMessage()));
    }

    // ✅ 중복된 deviceId (409 Conflict) → IllegalStateException으로 대체
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ErrorResponse> handleIllegalStateException(IllegalStateException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body((ErrorResponse) new DefaultErrorResponse("Duplicate deviceId", e.getMessage()));
    }

    // ✅ 기타 모든 예외 처리 (500 Internal Server Error)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body((ErrorResponse) new DefaultErrorResponse("Internal Server Error", e.getMessage()));
    }
}
