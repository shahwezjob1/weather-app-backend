package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppResponse;

public interface WeatherService {

    /**
     * Fetches the weather forecast for the given city.
     *
     * @param city The name of the city.
     * @return Weather data of the city.
     */
    WeatherAppResponse getWeather(String city);
}
