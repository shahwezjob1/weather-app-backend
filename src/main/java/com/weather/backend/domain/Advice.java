package com.weather.backend.domain;

import lombok.*;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Setter
public class Advice {
    private String condition;
    private String value;
}
