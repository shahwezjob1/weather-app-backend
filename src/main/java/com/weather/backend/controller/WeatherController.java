package com.weather.backend.controller;

import com.weather.backend.domain.WeatherAppResponse;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@Validated
public interface WeatherController {
    ResponseEntity<WeatherAppResponse> getWeatherForecast(@Pattern(regexp = "^[a-zA-Z ]+$", message = "City name must only contain letters and spaces") String city);
}
