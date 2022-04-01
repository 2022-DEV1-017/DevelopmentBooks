package com.digitalstork.bookskata.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());

        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(status)
                .errorCode(ErrorCode.DATA_VALIDATION.name())
                .message("Data Validation errors !")
                .subErrors(errors)
                .build();

        return new ResponseEntity<>(apiError, headers, status);
    }

    @ExceptionHandler(UnavailableBookException.class)
    protected ResponseEntity<Object> handleResourceNotFoundException(UnavailableBookException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND)
                .errorCode(ErrorCode.RESOURCE_NOT_FOUND.name())
                .message(ex.getMessage())
                .subErrors(new ArrayList<>())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }
}