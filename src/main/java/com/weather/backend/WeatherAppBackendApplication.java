package com.weather.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppBackendApplication.class, args);
	}

}
