package com.weather.service;

import com.weather.model.OpenWeatherAPIResponse;
import com.weather.model.WeatherForecastResponse;

public interface WeatherForecastProviderService {

    OpenWeatherAPIResponse getWeatherForecast(String city, int count);
}
