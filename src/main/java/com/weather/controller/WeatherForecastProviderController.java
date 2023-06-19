package com.weather.controller;

import com.weather.cache.CacheManager;
import com.weather.constants.APIConstants;
import com.weather.model.OpenWeatherAPIResponse;
import com.weather.model.WeatherForecastResponse;
import com.weather.service.WeatherForecastProviderService;
import com.weather.service.WeatherMessagePredictor;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.weather.constants.APIConstants.*;

@RestController
@Slf4j
public class WeatherForecastProviderController {

    @Autowired
    private WeatherForecastProviderService weatherForecastProviderService;

    @Autowired
    private CacheManager<OpenWeatherAPIResponse> cacheManager;

    @Autowired
    private WeatherMessagePredictor weatherMessagePredictor;

    @GetMapping(RETRIEVE_FORECAST)
    @CircuitBreaker(name = "detailsForOpenWeatherAPI",fallbackMethod=
            "openWeatherAPIFallBack")
    public ResponseEntity<WeatherForecastResponse> getWeather(@RequestParam("city") String city,
                                                              @RequestParam("count") int count) {
        log.info("Request received on endpoint {} for the city",RETRIEVE_FORECAST,city);
        OpenWeatherAPIResponse weatherResponse = weatherForecastProviderService.getWeatherForecast(city, count);
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        weatherForecastResponse.setErrocode(ERRORCODE_SUCCESS);
        weatherForecastResponse.setErrormessage(SUCCESS);
        weatherForecastResponse.setApiResponse(weatherResponse);
        return ResponseEntity.ok(weatherForecastResponse);
    }

    public ResponseEntity<WeatherForecastResponse> openWeatherAPIFallBack(String city, int count,
                                                                          Throwable t){
        log.info("Fallback method called for endpoint {} having city {} and count {}",
                RETRIEVE_FORECAST,city,count);
        // check in cache if we have the entry for the city
        OpenWeatherAPIResponse openWeatherAPIResponse = cacheManager.get(city);
        WeatherForecastResponse weatherForecastResponse = new WeatherForecastResponse();
        if(openWeatherAPIResponse==null) {
            weatherForecastResponse.setErrocode(ERRORCODE_FAIL);
            weatherForecastResponse.setErrormessage("Weather");
            weatherForecastResponse.setApiResponse(openWeatherAPIResponse);
            return ResponseEntity.ok(weatherForecastResponse);
        }
        weatherForecastResponse.setErrocode(ERRORCODE_SUCCESS);
        weatherForecastResponse.setErrormessage(SUCCESS);
        openWeatherAPIResponse.setWeatherCondition(weatherMessagePredictor.calculateWeatherMessage(
                openWeatherAPIResponse));
        weatherForecastResponse.setApiResponse(openWeatherAPIResponse);
        return ResponseEntity.ok(weatherForecastResponse);
    }
}
