package com.weather.backend.util;

import com.weather.backend.domain.Data;
import com.weather.backend.domain.Weather;
import com.weather.backend.dto.List;
import com.weather.backend.dto.OpenWeatherApiResponse;
import com.weather.backend.domain.WeatherAppResponse;

import java.util.ArrayList;

public class Helper {
    private Helper() {

    }

    public static WeatherAppResponse getAppResponse(OpenWeatherApiResponse response) {
        WeatherAppResponse weatherAppResponse = new WeatherAppResponse();
        weatherAppResponse.setCod(response.getCod());
        weatherAppResponse.setMessage(weatherAppResponse.getMessage());
        weatherAppResponse.setData(new Data());
        Data data = weatherAppResponse.getData();
        data.setCnt(response.getCnt());
        data.setCity(response.getCity().getName());
        data.setPopulation(response.getCity().getPopulation());
        data.setSunrise(response.getCity().getSunrise());
        data.setSunset(response.getCity().getSunset());
        data.setTimezone(response.getCity().getTimezone());
        data.setList(new ArrayList<>());
        for (List item : response.getList()) {
            Weather weather = new Weather();
            weather.setSummary(item.getWeather().get(0).getMain());
            weather.setDescription(item.getWeather().get(0).getDescription());
            weather.setDtTxt(item.getDtTxt());
            weather.setTempMax(item.getMain().getTempMax());
            weather.setTempMin(item.getMain().getTempMin());
            weather.setWindSpeed(item.getWind().getSpeed());
            weather.setIcon(item.getWeather().get(0).getIcon());
            data.getList().add(weather);
        }
        return weatherAppResponse;
    }
}
