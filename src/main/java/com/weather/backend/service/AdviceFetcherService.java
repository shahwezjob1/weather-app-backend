package com.weather.backend.service;

import com.weather.backend.domain.Advice;

import java.util.List;

public interface AdviceFetcherService {
    public List<Advice> getRules();
}
