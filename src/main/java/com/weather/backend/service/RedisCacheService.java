package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.WeatherAppResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class RedisCacheService implements CacheService {

    private final RedisTemplate<String, WeatherAppResponse> redisTemplate;
    private final AppProps props;

    @Override
    public WeatherAppResponse getFromCache(String key) {
        try {
            log.debug("RedisCacheService::getFromCache::" + key);
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.error("Exception when trying to get cache = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
            return null;
        }
    }

    @Override
    public void setInCache(String key, WeatherAppResponse value) {
        try {
            log.debug("RedisCacheService::setInCache::" + key);
            redisTemplate.opsForValue().set(key, value, props.getRedisTTL(), TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception when trying to set cache = " + e.getMessage());
            log.error(Arrays.toString(e.getStackTrace()));
        }
    }
}
