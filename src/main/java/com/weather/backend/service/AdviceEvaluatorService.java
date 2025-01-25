package com.weather.backend.service;

import com.weather.backend.domain.Weather;

public interface AdviceEvaluatorService {
    public String getAdvice(Weather weather);
}
