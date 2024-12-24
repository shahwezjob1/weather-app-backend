package com.weather.backend.advice;

import com.weather.backend.domain.WeatherAppErrorResponse;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WeatherAppErrorResponse> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        WeatherAppErrorResponse res = new WeatherAppErrorResponse("400", errorMessage);
        log.error(Arrays.toString(ex.getStackTrace()));
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<WeatherAppErrorResponse> handleHttpClientException(HttpClientErrorException ex) {
        log.error(Arrays.toString(ex.getStackTrace()));
        WeatherAppErrorResponse res = ex.getResponseBodyAs(WeatherAppErrorResponse.class);
        return new ResponseEntity<>(res, ex.getStatusCode());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WeatherAppErrorResponse> handleGeneralException(Exception ex) {
        WeatherAppErrorResponse error = new WeatherAppErrorResponse("500", "Internal Server Error");
        log.error("Error = " + ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
