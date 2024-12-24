package com.weather.backend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WeatherAppResponse {
    private String summary;
    private String description;
    private double temp;
    private double pressure;
    private double humidity;
    private double tempMin;
    private double tempMax;
    private double tempFeelsLike;
    private double visibility;
    private double windSpeed;
    private int sunrise;
    private int sunset;
    private double cloudPercentage;
}
