package com.todo.app.backend.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public final class ControllerAdviceUtil {

    private ControllerAdviceUtil() {}

    public static ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String title, Object detail) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now());
        errorResponse.put("status", status.value());
        errorResponse.put("error", status.getReasonPhrase());
        errorResponse.put("title", title);
        errorResponse.put("detail", detail);
        return new ResponseEntity<>(errorResponse, status);
    }

}
