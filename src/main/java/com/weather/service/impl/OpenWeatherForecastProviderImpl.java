package com.weather.service.impl;

import com.weather.api.restclient.OpenWeatherAPIProxy;
import com.weather.cache.CacheManager;
import com.weather.model.OpenWeatherAPIResponse;
import com.weather.service.WeatherForecastProviderService;
import com.weather.service.WeatherMessagePredictor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OpenWeatherForecastProviderImpl implements WeatherForecastProviderService {


     OpenWeatherAPIProxy openWeatherAPIProxy;

     CacheManager<OpenWeatherAPIResponse> cacheManager;
     WeatherMessagePredictor weatherMessagePredictor;

    @Autowired
    public OpenWeatherForecastProviderImpl(OpenWeatherAPIProxy openWeatherAPIProxy, CacheManager<OpenWeatherAPIResponse> cacheManager,
                                           WeatherMessagePredictor weatherMessagePredictor  ){
        this.openWeatherAPIProxy = openWeatherAPIProxy;
        this.cacheManager = cacheManager;
        this.weatherMessagePredictor = weatherMessagePredictor;
    }

    private final String SUCCESS200 = "200";
    @Value("${openweather.api.appid}")
    private String appId;

    @Value("${cache.ttl.minute.inmillis}")
    private int ttlminutes;

    @Override
    public OpenWeatherAPIResponse getWeatherForecast(String city, int count) {

        // check in cache if we have the entry for the city
        OpenWeatherAPIResponse openWeatherAPIResponse = cacheManager.get(city);
        if (openWeatherAPIResponse == null) {

            // fetch the data from the openweather API
            openWeatherAPIResponse = openWeatherAPIProxy.getWeatherForecast(city, appId, count);

            if (openWeatherAPIResponse.getCod().equalsIgnoreCase(SUCCESS200)) {
                log.info("Registering the response from openweather for the city {} into cache", city);
                cacheManager.put(city, openWeatherAPIResponse, ttlminutes * 5 * 1000);
            }
        }
        openWeatherAPIResponse.setWeatherCondition(weatherMessagePredictor.calculateWeatherMessage(openWeatherAPIResponse));
        return openWeatherAPIResponse;
    }
}
