package com.weather.backend.advice;

import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.exception.ServiceUnavailableException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
@Hidden
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceUnavailableException.class)
    public ResponseEntity<WeatherAppResponse> handleServiceUnavailableException(ServiceUnavailableException ex) {
        WeatherAppResponse res = new WeatherAppResponse("503", ex.getMessage(), null);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(res);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WeatherAppResponse> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        WeatherAppResponse res = new WeatherAppResponse("400", errorMessage, null);
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<WeatherAppResponse> handleHttpClientException(HttpClientErrorException.NotFound ex) {
        WeatherAppResponse res = ex.getResponseBodyAs(WeatherAppResponse.class);
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WeatherAppResponse> handleGeneralException(Exception ex) {
        WeatherAppResponse error = new WeatherAppResponse("500", "Internal Server Error", null);
        log.error("Error = " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
