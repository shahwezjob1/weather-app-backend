package com.weather.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 TODO create reactive version
 TODO write test cases
 TODO write readme md
 TODO swagger actuator error resolution or simply remove swagger
 */

@SpringBootApplication
public class WeatherAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppBackendApplication.class, args);
	}

}
