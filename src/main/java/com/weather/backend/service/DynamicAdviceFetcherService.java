package com.weather.backend.service;

import com.weather.backend.config.AppProps;
import com.weather.backend.domain.Advice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
@RequiredArgsConstructor
@Slf4j
public class DynamicAdviceFetcherService implements AdviceFetcherService {

    private final RedisTemplate<String, Advice> redisTemplate;
    private static final String KEY = "RULES";
    private final AtomicReference<List<Advice>> rules = new AtomicReference<>(Collections.emptyList());

    /**
     * Exposes the current immutable list of rules.
     *
     * @return immutable list of rules
     */

    @Override
    public List<Advice> getRules() {
        return rules.get();
    }

    /**
     * Periodically fetches rules and updates the list.
     * Falls back to the current list if fetching fails.
     */
    @Scheduled(fixedRateString = "${app.advice.fetch.interval.ms:600000}")
    private void fetchRules() {
        try {
            log.debug("Fetching Advices");
            List<Advice> fetchedAdvices = new ArrayList<>(redisTemplate.opsForSet().members(KEY));
            rules.set(Collections.unmodifiableList(fetchedAdvices));
        } catch (Exception e) {
            log.error("Error fetching rules from Redis: " + e.getMessage());
        }
    }
}
