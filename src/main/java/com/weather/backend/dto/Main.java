package com.weather.backend.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Main{
    private Double temp;
    @JsonProperty("feels_like")
    private Double feelsLike;
    @JsonProperty("temp_min")
    private Double tempMin;
    @JsonProperty("temp_max")
    private Double tempMax;
    private Double pressure;
    @JsonProperty("sea_level")
    private Double seaLevel;
    @JsonProperty("grnd_level")
    private Double grndLevel;
    private Double humidity;
    @JsonProperty("temp_kf")
    private Double tempKf;
}
