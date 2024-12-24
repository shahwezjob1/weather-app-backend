package com.weather.backend.service;

import com.weather.backend.config.OpenWeatherApiConfig;
import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.dto.OpenWeatherApiResponse;
import com.weather.backend.util.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class OpenWeatherApiService implements WeatherApiService {

    private final OpenWeatherApiConfig config;
    private final RestTemplate restTemplate;

    @Override
    public WeatherAppResponse getWeather(String city) {
        String url = config.getUrl() + "?q=" + city + "&appId=" + config.getKey() + "&units=metric";
        OpenWeatherApiResponse response = restTemplate.getForObject(url, OpenWeatherApiResponse.class);
        return Helper.getAppResponse(response);
    }
}
