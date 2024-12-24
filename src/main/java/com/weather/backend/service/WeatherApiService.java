package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppResponse;

public interface WeatherApiService {
    /**
     * Fetches the weather forecast for a given city from the public API.
     *
     * @param city The name of the city.
     * @return OpenWeatherResponse containing the weather data.
     */
    WeatherAppResponse getWeather(String city);
}
