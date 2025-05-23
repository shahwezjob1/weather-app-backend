package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.Weather;
import com.weather.backend.domain.WeatherAppMessage;
import com.weather.backend.domain.WeatherAppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
@Profile("!minimal")
public class OpenWeatherService implements WeatherService {
    private final CacheService cacheService;
    private final WeatherApiService weatherApiService;
    private final MessageService messageService;
    private final AppProps appProps;
    private final AdviceEvaluatorService adviceEvaluatorService;

    @Override
    public WeatherAppResponse getWeather(String city) {
        log.debug("OpenWeatherService::getWeather::" + city);
        WeatherAppResponse cachedResponse = cacheService.getFromCache(city);
        if (cachedResponse != null) {
            messageService.sendMessage(new WeatherAppMessage(city));
            return cachedResponse;
        }
        WeatherAppResponse appResponse = weatherApiService.getWeather(city);
        for (Weather weather : appResponse.getData().getList()) {
            weather.setIcon(appProps.getIconUrl() + weather.getIcon() + "@2x.png");
            weather.setAdvice(adviceEvaluatorService.getAdvice(weather));
        }
        cacheService.setInCache(city, appResponse);
        messageService.sendMessage(new WeatherAppMessage(city));
        return appResponse;
    }
}
