package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppResponse;

public interface CacheService {
    /**
     * Retrieves cached weather data for the given key.
     *
     * @param key The cache key (typically the city name).
     * @return Weather data or null if no data is cached.
     */
    WeatherAppResponse getFromCache(String key);

    /**
     * Caches weather data with the given key.
     *
     * @param key The cache key (typically the city name).
     * @param value The weather data to cache.
     */
    void setInCache(String key, WeatherAppResponse value);
}
