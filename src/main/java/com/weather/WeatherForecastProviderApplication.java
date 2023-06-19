package com.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WeatherForecastProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForecastProviderApplication.class, args);
	}

}
