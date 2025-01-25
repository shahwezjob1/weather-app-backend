package com.weather.backend.exception;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@ToString
public class ServiceUnavailableException extends RuntimeException {
    private final String message;
}
