package com.weather.backend;

import com.weather.backend.config.OpenWeatherApiConfigDev;
import com.weather.backend.config.OpenWeatherApiConfigProd;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@Profile("dev")
@Slf4j
public class ApplicationStartupTaskDev {

    @Autowired
    private OpenWeatherApiConfigDev config;

    @EventListener(ApplicationReadyEvent.class)
    public void executeAfterStartup() {
        log.info("Application started in Dev Mode.");
        log.info("-----------------------------------------------");
        log.info(config.getUrl());
        log.info(config.getKey());
        log.info("-----------------------------------------------");
    }
}

