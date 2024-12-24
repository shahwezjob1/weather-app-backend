package com.weather.backend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class WeatherAppErrorResponse {
    String cod;
    String message;
}