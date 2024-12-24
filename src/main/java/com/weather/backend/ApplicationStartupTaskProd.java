package com.weather.backend;

import com.weather.backend.config.OpenWeatherApiConfigProd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("prod")
@Slf4j
public class ApplicationStartupTaskProd {

    @Autowired
    private OpenWeatherApiConfigProd config;

    @EventListener(ApplicationReadyEvent.class)
    public void executeAfterStartup() {
        log.info("Application started in Prod Mode.");
        log.info("-----------------------------------------------");
        log.info(config.getUrl());
        log.info(config.getKeys().toString());
        log.info("-----------------------------------------------");
    }
}

