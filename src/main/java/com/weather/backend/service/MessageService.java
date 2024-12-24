package com.weather.backend.service;

import com.weather.backend.domain.WeatherAppMessage;

public interface MessageService {

    /**
     *
     * @param message data to send (typically the city name).
     */
    public void sendMessage(WeatherAppMessage message);
}
