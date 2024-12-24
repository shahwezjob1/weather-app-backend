package com.weather.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 TODO add logback config such that it keeps creating new log files after fixed intervals of time
 TODO add open telemetry config for dev and prod
 TODO create reactive version
 TODO write test cases
 */

@SpringBootApplication
public class WeatherAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherAppBackendApplication.class, args);
	}

}
