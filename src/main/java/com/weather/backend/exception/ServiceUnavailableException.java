package com.weather.backend.exception;

public class ServiceUnavailableException extends RuntimeException {
    private static final String MESSAGE = "Service is currently unavailable. Please try again later.";

    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
