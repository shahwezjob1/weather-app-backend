package com.weather.backend.controller;

import com.weather.backend.advice.GlobalExceptionHandler;
import com.weather.backend.domain.Data;
import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.exception.ServiceUnavailableException;
import com.weather.backend.service.WeatherService;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Collections;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class OpenWeatherControllerTest {

    @InjectMocks
    private OpenWeatherController openWeatherController;

    @Mock
    private WeatherService weatherService;

    private MockMvc mockMvc;

    private static final String CITY = "London";
    private static final String INVALID_CITY = "London123";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(openWeatherController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }

    @Test
    void testGetWeatherForecast_Successful() throws Exception {
        WeatherAppResponse mockResponse = new WeatherAppResponse("200", null, new Data());
        when(weatherService.getWeather(CITY)).thenReturn(mockResponse);

        mockMvc.perform(get("/weather").param("city", CITY).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.cod").value("200"));

        verify(weatherService, times(1)).getWeather(CITY);
    }

    @Test
    void testGetWeatherForecast_CityNotFound() throws Exception {
        HttpClientErrorException.NotFound ex = mock(HttpClientErrorException.NotFound.class);
        WeatherAppResponse res = new WeatherAppResponse("404", "City Not Found", null);
        when(ex.getResponseBodyAs(WeatherAppResponse.class)).thenReturn(res);
        when(ex.getStatusCode()).thenReturn(HttpStatusCode.valueOf(404));
        when(weatherService.getWeather(CITY)).thenThrow(ex);

        mockMvc.perform(get("/weather").param("city", CITY).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("City Not Found"));

        verify(weatherService, times(1)).getWeather(CITY);
    }

    @Test
    void testGetWeatherForecast_InternalServerError() throws Exception {
        when(weatherService.getWeather(CITY)).thenThrow(new RuntimeException("Internal server error"));

        mockMvc.perform(get("/weather").param("city", CITY).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message").value("Internal Server Error"))
                .andExpect(jsonPath("$.cod").value("500"));

        verify(weatherService, times(1)).getWeather(CITY);
    }

    @Test
    void testGetWeatherForecast_ServiceUnavailable() throws Exception {
        when(weatherService.getWeather(CITY)).thenThrow(new ServiceUnavailableException());

        mockMvc.perform(get("/weather").param("city", CITY).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isServiceUnavailable())
                .andExpect(jsonPath("$.message").value("Service is currently unavailable. Please try again later."))
                .andExpect(jsonPath("$.cod").value("503"));

        verify(weatherService, times(1)).getWeather(CITY);
    }

    @Test
    void testGetWeatherForecast_ConstraintViolation() throws Exception {
        ConstraintViolation<String> mockViolation = mock(ConstraintViolation.class);
        when(mockViolation.getMessage()).thenReturn("City name must only contain letters and spaces");
        when(mockViolation.getPropertyPath()).thenReturn(mock(Path.class));

        Set<ConstraintViolation<String>> violations = Collections.singleton(mockViolation);

        ConstraintViolationException ex = new ConstraintViolationException(violations);

        when(weatherService.getWeather(INVALID_CITY)).thenThrow(ex);

        mockMvc.perform(get("/weather").param("city", INVALID_CITY).accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("City name must only contain letters and spaces"))
                .andExpect(jsonPath("$.cod").value("400"));

        verify(weatherService, times(1)).getWeather(INVALID_CITY);
    }

}

