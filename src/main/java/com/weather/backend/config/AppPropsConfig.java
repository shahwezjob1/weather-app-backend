package com.weather.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppPropsConfig implements AppProps {
    @Value("${app.kafka.producer.topic}")
    private String kafkaTopic;

    @Value("${app.cache.ttl.seconds:600}")
    private int redisTTL;

    @Value("${app.weather.icon.url}")
    private String iconUrl;

    @Override
    public String getKafkaTopic() {
        return kafkaTopic;
    }

    @Override
    public int getRedisTTL() {
        return redisTTL;
    }

    @Override
    public String getIconUrl() {
        return iconUrl;
    }

}
