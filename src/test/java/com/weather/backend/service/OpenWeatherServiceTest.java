package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.Data;
import com.weather.backend.domain.Weather;
import com.weather.backend.domain.WeatherAppMessage;
import com.weather.backend.domain.WeatherAppResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OpenWeatherServiceTest {
    @InjectMocks
    private OpenWeatherService openWeatherService;
    @Mock
    private WeatherApiService weatherApiService;
    @Mock
    private MessageService messageService;
    @Mock
    private CacheService cacheService;
    @Mock
    private AdviceEvaluatorService adviceEvaluatorService;
    @Mock
    private AppProps appProps;

    private static final String CITY = "London";
    private static final WeatherAppMessage MESSAGE = new WeatherAppMessage(CITY);

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather_WithCachedResponse() {
        WeatherAppResponse cachedResponse = new WeatherAppResponse();
        when(cacheService.getFromCache(CITY)).thenReturn(cachedResponse);
        WeatherAppResponse result = openWeatherService.getWeather(CITY);
        assertEquals(cachedResponse, result);
        verify(cacheService, times(1)).getFromCache(CITY);
        verify(weatherApiService, never()).getWeather(CITY);
        verify(messageService, times(1)).sendMessage(MESSAGE);
    }

    @Test
    void testGetWeather_WithoutCachedResponse() {
        Data data = mock(Data.class);
        Weather weather = mock(Weather.class);
        WeatherAppResponse apiResponse = mock(WeatherAppResponse.class);
        when(cacheService.getFromCache(CITY)).thenReturn(null);
        when(weatherApiService.getWeather(CITY)).thenReturn(apiResponse);
        when(apiResponse.getData()).thenReturn(data);
        when(data.getList()).thenReturn(List.of(weather));
        when(appProps.getIconUrl()).thenReturn("url/");
        when(weather.getIcon()).thenReturn("icon");
        doNothing().when(weather).setIcon("url/icon@2x.png");
        when(adviceEvaluatorService.getAdvice(weather)).thenReturn("advice");
        doNothing().when(weather).setAdvice("advice");
        WeatherAppResponse result = openWeatherService.getWeather(CITY);
        assertEquals(apiResponse, result);
        verify(cacheService, times(1)).getFromCache(CITY);
        verify(weatherApiService, times(1)).getWeather(CITY);
        verify(cacheService, times(1)).setInCache(CITY, apiResponse);
        verify(messageService, times(1)).sendMessage(MESSAGE);
        verify(adviceEvaluatorService, times(1)).getAdvice(weather);
        verify(weather, times(1)).setAdvice("advice");
        verify(weather, times(1)).setIcon("url/icon@2x.png");
        verify(weather, times(1)).getIcon();
        verify(data, times(1)).getList();
        verify(apiResponse, times(1)).getData();
    }

}
