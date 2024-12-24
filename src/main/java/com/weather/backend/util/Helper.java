package com.weather.backend.util;

import com.weather.backend.dto.OpenWeatherApiResponse;
import com.weather.backend.domain.WeatherAppResponse;

public class Helper {
    private Helper() {

    }

    public static WeatherAppResponse getAppResponse(OpenWeatherApiResponse response) {
        return new WeatherAppResponse(response.getWeather().get(0).getMain(),
                response.getWeather().get(0).getDescription(),
                response.getMain().getTemp(),
                response.getMain().getPressure(),
                response.getMain().getHumidity(),
                response.getMain().getTempMin(),
                response.getMain().getTempMax(),
                response.getMain().getTempMax(),
                response.getVisibility(),
                response.getWind().getSpeed(),
                response.getSys().getSunrise(),
                response.getSys().getSunset(),
                response.getClouds().getAll()
        );
    }
}
