package com.digitalstork.developmentbooks.exceptions;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiError {
    private LocalDateTime timestamp;
    private HttpStatus status;
    private String errorCode;
    private String message;
    private List<String> subErrors;
}
