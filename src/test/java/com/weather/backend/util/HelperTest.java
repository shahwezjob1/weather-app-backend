package com.weather.backend.util;

import com.weather.backend.domain.WeatherAppResponse;
import com.weather.backend.dto.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class HelperTest {

    private static final int CITY_ID = 1;
    private static final String CITY_NAME = "TestCity";
    private static final Coord CITY_COORD = new Coord(10.0, 20.0);
    private static final String CITY_COUNTRY = "TestCountry";
    private static final int CITY_POPULATION = 1000000;
    private static final int CITY_TIMEZONE = 3600;
    private static final int CITY_SUNRISE = 1609459200;
    private static final int CITY_SUNSET = 1609498800;

    private static final int WEATHER_ID = 800;
    private static final String WEATHER_MAIN = "Clear";
    private static final String WEATHER_DESCRIPTION = "clear sky";
    private static final String WEATHER_ICON = "01d";

    private static final double MAIN_TEMP = 300.0;
    private static final double MAIN_FEELS_LIKE = 298.0;
    private static final double MAIN_TEMP_MIN = 295.0;
    private static final double MAIN_TEMP_MAX = 305.0;
    private static final double MAIN_PRESSURE = 1013.0;
    private static final double MAIN_SEA_LEVEL = 1013.0;
    private static final double MAIN_GRND_LEVEL = 1013.0;
    private static final double MAIN_HUMIDITY = 50.0;
    private static final double MAIN_TEMP_KF = 0.0;

    private static final double WIND_SPEED = 5.0;
    private static final int WIND_DEG = 180;
    private static final double WIND_GUST = 7.0;

    private static final double CLOUDS_ALL = 0.0;

    private static final String SYS_POD = "d";

    private static final int LIST_DT = 1609459200;
    private static final double LIST_VISIBILITY = 10000.0;
    private static final int LIST_POP = 0;
    private static final String LIST_DT_TXT = "2021-01-01 00:00:00";

    private static final String RESPONSE_COD = "200";
    private static final int RESPONSE_MESSAGE = 0;
    private static final int RESPONSE_CNT = 1;

    private static OpenWeatherApiResponse mockApiResponse;

    @BeforeAll
    public static void setUp() {
        City city = new City(CITY_ID, CITY_NAME, CITY_COORD, CITY_COUNTRY, CITY_POPULATION, CITY_TIMEZONE, CITY_SUNRISE, CITY_SUNSET);

        ArrayList<Weather> weatherList = new ArrayList<>();
        weatherList.add(new Weather(WEATHER_ID, WEATHER_MAIN, WEATHER_DESCRIPTION, WEATHER_ICON));

        Main main = new Main(MAIN_TEMP, MAIN_FEELS_LIKE, MAIN_TEMP_MIN, MAIN_TEMP_MAX, MAIN_PRESSURE, MAIN_SEA_LEVEL, MAIN_GRND_LEVEL, MAIN_HUMIDITY, MAIN_TEMP_KF);
        Wind wind = new Wind(WIND_SPEED, WIND_DEG, WIND_GUST);
        Clouds clouds = new Clouds(CLOUDS_ALL);
        Sys sys = new Sys(SYS_POD);

        List listItem = new List(LIST_DT, main, weatherList, clouds, wind, LIST_VISIBILITY, LIST_POP, sys, LIST_DT_TXT);

        ArrayList<List> list = new ArrayList<>();
        list.add(listItem);

        mockApiResponse = new OpenWeatherApiResponse(RESPONSE_COD, RESPONSE_MESSAGE, RESPONSE_CNT, list, city);
    }

    @Test
    public void testGetAppResponse() {
        WeatherAppResponse response = Helper.getAppResponse(mockApiResponse);

        assertNotNull(response);
        assertEquals(RESPONSE_COD, response.getCod());
        assertEquals(RESPONSE_CNT, response.getData().getCnt().intValue());
        assertEquals(CITY_NAME, response.getData().getCity());
        assertEquals(CITY_POPULATION, response.getData().getPopulation().intValue());
        assertEquals(CITY_SUNRISE, response.getData().getSunrise().intValue());
        assertEquals(CITY_SUNSET, response.getData().getSunset().intValue());
        assertEquals(CITY_TIMEZONE, response.getData().getTimezone().intValue());
        assertEquals(1, response.getData().getList().size());

        com.weather.backend.domain.Weather weather = response.getData().getList().get(0);
        assertEquals(WEATHER_MAIN, weather.getSummary());
        assertEquals(WEATHER_DESCRIPTION, weather.getDescription());
        assertEquals(LIST_DT_TXT, weather.getDtTxt());
        assertEquals(MAIN_TEMP_MAX, weather.getTempMax(), 0.0);
        assertEquals(MAIN_TEMP_MIN, weather.getTempMin(), 0.0);
        assertEquals(WIND_SPEED, weather.getWindSpeed(), 0.0);
        assertEquals(WEATHER_ICON, weather.getIcon());
    }
}
