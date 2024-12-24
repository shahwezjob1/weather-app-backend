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
    private Coord coord;
    private ArrayList<Weather> weather;
    private String base;
    private Main main;
    private Double visibility;
    private Wind wind;
    private Clouds clouds;
    private Integer dt;
    private Sys sys;
    private Integer id;
    private String name;
    private Integer cod;
}
