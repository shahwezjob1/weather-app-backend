package com.weather.backend.service;

import com.weather.backend.config.OpenWeatherApiConfig;
import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.dto.OpenWeatherApiResponse;
import com.weather.backend.util.Helper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OpenWeatherApiServiceTest {

    @InjectMocks
    private OpenWeatherApiService openWeatherApiService;

    @Mock
    private OpenWeatherApiConfig config;

    @Mock
    private RestTemplate restTemplate;

    private static final MockedStatic<Helper> HELPER_MOCK = mockStatic(Helper.class);

    private static final String CITY = "London";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather";
    private static final String API_KEY = "test-key";
    private static final String EXPECTED_URL = API_URL + "?q=" + CITY + "&appId=" + API_KEY + "&units=metric";

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetWeather_Success() {
        OpenWeatherApiResponse mockApiResponse = mock(OpenWeatherApiResponse.class);
        WeatherAppResponse expectedResponse = mock(WeatherAppResponse.class);
        when(config.getUrl()).thenReturn(API_URL);
        when(config.getKey()).thenReturn(API_KEY);
        when(restTemplate.getForObject(EXPECTED_URL, OpenWeatherApiResponse.class)).thenReturn(mockApiResponse);
        HELPER_MOCK.when(() -> Helper.getAppResponse(mockApiResponse)).thenReturn(expectedResponse);
        WeatherAppResponse result = openWeatherApiService.getWeather(CITY);
        assertEquals(expectedResponse, result);
        verify(config, times(1)).getUrl();
        verify(config, times(1)).getKey();
        verify(restTemplate, times(1)).getForObject(EXPECTED_URL, OpenWeatherApiResponse.class);
        HELPER_MOCK.verify(() -> Helper.getAppResponse(mockApiResponse), times(1));
    }

}