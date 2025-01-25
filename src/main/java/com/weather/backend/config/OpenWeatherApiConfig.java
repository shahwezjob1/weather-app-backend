package com.weather.backend.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@Validated
public class OpenWeatherApiConfig implements OpenWeatherApiProps {
    @NotBlank
    @Value("${app.open.weather.api.url}")
    private String url;
    @NotEmpty
    @Value("${app.open.weather.api.keys}")
    private List<String> keys;
    private int counter;
    /**
     * Returns an API key in round-robin fashion.
     *
     * @return the next API key
     */

    @Override
    public synchronized String getKey() {
        String key = keys.get(counter);
        counter = (counter + 1) % keys.size();
        return key;
    }

    @Override
    public String getUrl() {
        return url;
    }
}