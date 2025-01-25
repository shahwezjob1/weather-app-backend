package com.weather.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class City{
    private int id;
    private String name;
    private Coord coord;
    private String country;
    private int population;
    private int timezone;
    private int sunrise;
    private int sunset;
}
