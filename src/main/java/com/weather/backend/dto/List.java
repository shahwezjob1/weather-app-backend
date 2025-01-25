package com.weather.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class List {
    private Integer dt;
    private Main main;
    private ArrayList<Weather> weather;
    private Clouds clouds;
    private Wind wind;
    private Double visibility;
    private Integer pop;
    private Sys sys;
    @JsonProperty("dt_txt")
    private String dtTxt;
}
