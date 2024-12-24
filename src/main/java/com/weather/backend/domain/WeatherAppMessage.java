package com.weather.backend.domain;

import lombok.*;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WeatherAppMessage implements Serializable {
    private String city;
}
