package com.todo.app.backend.controller.advice;

import com.todo.app.backend.exception.NotAuthorizedException;
import com.todo.app.backend.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@Slf4j
@RestControllerAdvice
public class TodoControllerAdvice {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Todo not found: {}", ex.getMessage());
        return ControllerAdviceUtil.buildErrorResponse(HttpStatus.BAD_REQUEST, "Todo not found", ex.getMessage());
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleNotAuthorizedException(NotAuthorizedException ex) {
        log.error("User has not right access to resource: {}", ex.getMessage());
        return ControllerAdviceUtil.buildErrorResponse(HttpStatus.FORBIDDEN, "User has not right access to resource", ex.getMessage());
    }

}
