package com.weather.backend.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Weather {
    private String dtTxt;
    private String summary;
    private String description;
    private Double tempMin;
    private Double tempMax;
    private Double windSpeed;
    private String icon;
    private String advice;
}
