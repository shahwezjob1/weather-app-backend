package com.weather.backend.controller;

import jakarta.validation.constraints.Pattern;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class WeatherControllerTest {

    @Test
    void testCityPatternValidation() throws NoSuchMethodException {
        final String validCity = "New York";
        final String invalidCity = "123Invalid@";

        final String regexp = Arrays.stream(
                        WeatherController.class.getMethod("getWeatherForecast", String.class)
                                .getParameters()[0]
                                .getAnnotationsByType(Pattern.class)
                )
                .findFirst()
                .get()
                .regexp();

        Assertions.assertThat(validCity)
                .as("Ensure that the regexp from annotation successfully matches valid city '%s'", validCity)
                .matches(regexp);

        Assertions.assertThat(invalidCity)
                .as("Ensure that the regexp from annotation does not match invalid city '%s'", invalidCity)
                .doesNotMatch(regexp);
    }
}

