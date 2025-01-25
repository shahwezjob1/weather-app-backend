package com.weather.backend.config;

public interface AppProps {

    String getKafkaTopic();

    int getRedisTTL();

    String getIconUrl();
}
