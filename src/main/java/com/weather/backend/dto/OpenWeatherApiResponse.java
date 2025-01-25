package com.weather.backend.dto;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class OpenWeatherApiResponse {
    private String cod;
    private Integer message;
    private Integer cnt;
    private ArrayList<List> list;
    private City city;
}
