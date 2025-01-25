package com.weather.backend.advice;

import com.weather.backend.controller.OpenWeatherController;
import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.exception.ServiceUnavailableException;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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
    public ResponseEntity<EntityModel<WeatherAppResponse>> handleServiceUnavailableException(ServiceUnavailableException ex) {
        WeatherAppResponse res = new WeatherAppResponse("503", ex.getMessage(), null);
        res.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OpenWeatherController.class)
                .getWeatherForecast(null)).withRel("try-after-some-time"));
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(res);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<EntityModel<WeatherAppResponse>> handleValidationExceptions(ConstraintViolationException ex) {
        String errorMessage = ex.getConstraintViolations().iterator().next().getMessage();
        WeatherAppResponse res = new WeatherAppResponse("400", errorMessage, null);
        res.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OpenWeatherController.class)
                .getWeatherForecast(null)).withRel("try-with-valid-city-name"));
        return ResponseEntity.badRequest().body(res);
    }

    @ExceptionHandler(HttpClientErrorException.NotFound.class)
    public ResponseEntity<EntityModel<WeatherAppResponse>> handleHttpClientException() {
        WeatherAppResponse res = new WeatherAppResponse("404", "City Not Found", null);
        res.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OpenWeatherController.class)
                .getWeatherForecast(null)).withRel("try-with-city-which-exists"));
        return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EntityModel<WeatherAppResponse>> handleGeneralException(Exception ex) {
        log.error("Error = " + ex.getMessage());
        WeatherAppResponse res = new WeatherAppResponse("500", "Internal Server Error", null);
        res.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(OpenWeatherController.class)
                .getWeatherForecast(null)).withRel("try-again"));
        return new ResponseEntity<>(res, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
