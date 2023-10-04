package com.tv.demo.simpleecommerce.controller.advice;

import com.tv.demo.simpleecommerce.dto.ErrorResponse;
import com.tv.demo.simpleecommerce.exception.ProductAlreadyExistsException;
import com.tv.demo.simpleecommerce.exception.RelationshipEntityNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFound(EntityNotFoundException exception) {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.NOT_FOUND.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RelationshipEntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> relationshipEntityNotFound(RelationshipEntityNotFoundException exception) {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.BAD_REQUEST.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ProductAlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> entityAlreadyExists(Exception exception) {
        var errorResponse = ErrorResponse.builder()
                .message(exception.getMessage())
                .code(HttpStatus.CONFLICT.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> constraintViolationException(ConstraintViolationException exception) {
        var errors = exception.getConstraintViolations().stream()
                .collect(Collectors.toMap(
                        this::getPropertyPath,
                        ConstraintViolation::getMessage
                ));

        var response = ErrorResponse.builder()
                .errors(errors)
                .code(HttpStatus.BAD_REQUEST.toString())
                .dateTime(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private String getPropertyPath(ConstraintViolation<?> exception) {
        return exception.getPropertyPath().toString();
    }
}
