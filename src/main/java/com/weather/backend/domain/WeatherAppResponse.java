package com.weather.backend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class WeatherAppResponse {
    private String cod;
    private String message;
    private Data data;
}
