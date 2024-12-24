package com.weather.backend.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Weather{
    private Integer id;
    private String main;
    private String description;
    private String icon;
}
