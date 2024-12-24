package com.weather.backend.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Configuration
@Setter
@Getter
@Profile("prod")
@ConfigurationProperties(prefix = "app.open.weather.api")
@Validated
public class OpenWeatherApiConfigProd implements OpenWeatherApiConfig {
    @NotBlank
    private String url;
    @NotEmpty
    private List<String> keys;
    private int counter;
    /**
     * Returns an API key in round-robin fashion.
     *
     * @return the next API key
     */
    public synchronized String getKey() {
        String key = keys.get(counter);
        counter = (counter + 1) % keys.size();
        return key;
    }
}