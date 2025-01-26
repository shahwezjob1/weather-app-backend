package com.weather.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Links;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = false)
public class WeatherAppResponse extends EntityModel<WeatherAppResponse> {
    private String cod;
    private String message;
    private Data data;
    @JsonIgnore
    @Override
    public Links getLinks() {
        return super.getLinks();
    }
    @Override
    @JsonIgnore
    public WeatherAppResponse getContent() {
        return super.getContent();
    }
}
