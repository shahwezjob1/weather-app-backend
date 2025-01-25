package com.weather.backend.service;

import com.weather.backend.config.OpenWeatherApiProps;
import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.dto.OpenWeatherApiResponse;
import com.weather.backend.exception.ServiceUnavailableException;
import com.weather.backend.util.Helper;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherApiService implements WeatherApiService {

    private final OpenWeatherApiProps apiProps;
    private final RestTemplate restTemplate;

    @Override
    @CircuitBreaker(name = "apiCB", fallbackMethod = "fallback")
    public WeatherAppResponse getWeather(String city) {
        String url = apiProps.getUrl() + "?q=" + city + "&appId=" + apiProps.getKey() + "&units=metric";
        log.debug("OpenWeatherApiService::getWeather::" + city);
        OpenWeatherApiResponse response = restTemplate.getForObject(url, OpenWeatherApiResponse.class);
        return Helper.getAppResponse(response);
    }

    public WeatherAppResponse fallback(String city, Throwable ex) {
        log.error("OpenWeatherApiService::fallback::" + city + "::" + ex.getMessage());
        throw new ServiceUnavailableException("Service is currently unavailable. Please try again later.");
    }

    public WeatherAppResponse fallback(String city, HttpClientErrorException.NotFound ex) {
        throw ex;
    }

}
