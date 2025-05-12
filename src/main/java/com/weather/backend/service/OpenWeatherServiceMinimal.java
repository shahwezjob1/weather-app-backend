package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.Weather;
import com.weather.backend.domain.WeatherAppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("minimal")
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherServiceMinimal implements WeatherService {
    private final CacheService cacheService;
    private final WeatherApiService weatherApiService;
    private final AppProps appProps;
    private final AdviceEvaluatorService adviceEvaluatorService;

    @Override
    public WeatherAppResponse getWeather(String city) {
        log.debug("OpenWeatherServiceMinimal::getWeather::" + city);
        WeatherAppResponse cachedResponse = cacheService.getFromCache(city);
        if (cachedResponse != null) {
            return cachedResponse;
        }
        WeatherAppResponse appResponse = weatherApiService.getWeather(city);
        for (Weather weather : appResponse.getData().getList()) {
            weather.setIcon(appProps.getIconUrl() + weather.getIcon() + "@2x.png");
            weather.setAdvice(adviceEvaluatorService.getAdvice(weather));
        }
        cacheService.setInCache(city, appResponse);
        return appResponse;
    }
}
