package com.weather.backend.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Getter
@Setter
@Profile("dev")
public class OpenWeatherApiConfigDev implements OpenWeatherApiConfig {
    @Value("${app.open.weather.api.url}")
    String url;
    @Value("${app.open.weather.api.key}")
    String key;
}
