package com.onilicious.EcommerceStartUp.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;
    private final String path;
    private final List<String> errors;

    public static ApiError of(int status, String message, String path) {
        return ApiError.builder()
                .status(status)
                .message(message)
                .timestamp(LocalDateTime.now())
                .path(path)
                .build();
    }
}
