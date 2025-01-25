package com.weather.backend.domain;

import lombok.*;
import org.springframework.hateoas.EntityModel;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class WeatherAppResponse extends EntityModel<WeatherAppResponse> {
    private String cod;
    private String message;
    private Data data;
}
