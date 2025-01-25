package com.weather.backend.domain;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Data {
    private Integer cnt;
    private List<Weather> list;
    private String city;
    private Integer population;
    private Integer timezone;
    private Integer sunrise;
    private Integer sunset;
}
