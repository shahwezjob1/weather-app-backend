package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.WeatherAppResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RedisCacheServiceTest {

    @InjectMocks
    private RedisCacheService redisCacheService;

    @Mock
    private RedisTemplate<String, WeatherAppResponse> redisTemplate;

    @Mock
    private ValueOperations<String, WeatherAppResponse> valueOperations;

    @Mock
    private AppProps props;

    private static final String CACHE_KEY = "testCity";
    private static final int TTL = 60;
    private static final WeatherAppResponse CACHE_VALUE = new WeatherAppResponse();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    void testGetFromCache_Successful() {
        when(valueOperations.get(CACHE_KEY)).thenReturn(CACHE_VALUE);
        WeatherAppResponse result = redisCacheService.getFromCache(CACHE_KEY);
        assertEquals(CACHE_VALUE, result);
        verify(redisTemplate.opsForValue(), times(1)).get(CACHE_KEY);
    }

    @Test
    void testGetFromCache_ExceptionHandling() {
        when(valueOperations.get(CACHE_KEY)).thenThrow(new RuntimeException("Redis error"));
        WeatherAppResponse result = redisCacheService.getFromCache(CACHE_KEY);
        assertNull(result);
        verify(redisTemplate.opsForValue(), times(1)).get(CACHE_KEY);
    }

    @Test
    void testSetInCache_Successful() {
        when(props.getRedisTTL()).thenReturn(TTL);
        doNothing().when(valueOperations).set(CACHE_KEY, CACHE_VALUE, TTL, TimeUnit.SECONDS);
        redisCacheService.setInCache(CACHE_KEY, CACHE_VALUE);
        verify(redisTemplate.opsForValue(), times(1))
                .set(CACHE_KEY, CACHE_VALUE, TTL, TimeUnit.SECONDS);
    }

    @Test
    void testSetInCache_ExceptionHandling() {
        when(props.getRedisTTL()).thenReturn(TTL);
        doThrow(new RuntimeException("Redis error")).when(valueOperations)
                .set(CACHE_KEY, CACHE_VALUE, TTL, TimeUnit.SECONDS);
        assertDoesNotThrow(() -> redisCacheService.setInCache(CACHE_KEY, CACHE_VALUE));
        verify(redisTemplate.opsForValue(), times(1))
                .set(CACHE_KEY, CACHE_VALUE, TTL, TimeUnit.SECONDS);
    }
}

