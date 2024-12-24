package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppMessage;
import com.weather.backend.domain.WeatherAppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherService implements WeatherService {
    private final CacheService cacheService;
    private final WeatherApiService weatherApiService;
    private final MessageService messageService;

    @Override
    public WeatherAppResponse getWeather(String city) {
        log.debug("OpenWeatherService::getWeather");
        WeatherAppResponse cachedResponse = cacheService.getFromCache(city);
        if (cachedResponse != null) {
            messageService.sendMessage(new WeatherAppMessage(city));
            return cachedResponse;
        }
        WeatherAppResponse apiResponse = weatherApiService.getWeather(city);
        cacheService.setInCache(city, apiResponse);
        messageService.sendMessage(new WeatherAppMessage(city));
        return apiResponse;
    }
}
