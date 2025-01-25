package com.weather.backend.controller;

import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.service.WeatherService;
import com.weather.backend.util.SampleResponseJson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weather")
@Slf4j
@RequiredArgsConstructor
public class OpenWeatherController implements WeatherController {
    private final WeatherService weatherService;

    @Operation(summary = "Get weather details for a city")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved weather data", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherAppResponse.class), examples = @ExampleObject(value = SampleResponseJson.SCHEMA_200))),
            @ApiResponse(responseCode = "404", description = "City not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherAppResponse.class), examples = @ExampleObject(value = SampleResponseJson.SCHEMA_404))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherAppResponse.class), examples = @ExampleObject(value = SampleResponseJson.SCHEMA_500))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WeatherAppResponse.class), examples = @ExampleObject(value = SampleResponseJson.SCHEMA_400)))
    })
    @GetMapping
    public ResponseEntity<WeatherAppResponse> getWeatherForecast(String city) {
        log.debug("WeatherController::getWeatherForecast::" + city);
        WeatherAppResponse response = weatherService.getWeather(city);
        return ResponseEntity.ok(response);
    }
}
