package com.weather.api.restclient;

import com.weather.constants.APIConstants;
import com.weather.model.OpenWeatherAPIResponse;
import com.weather.model.WeatherForecastResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = APIConstants.OPENWEATHERAPI, url = "${openweather.api.url}")
public interface OpenWeatherAPIProxy {

    @GetMapping(APIConstants.VERSION)
    OpenWeatherAPIResponse getWeatherForecast(@RequestParam(APIConstants.Q) String city,
                                              @RequestParam(APIConstants.APIKEY) String apiKey,
                                              @RequestParam(APIConstants.COUNT) int count);

}
