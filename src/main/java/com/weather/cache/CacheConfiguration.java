package com.weather.cache;

import com.weather.model.OpenWeatherAPIResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CacheConfiguration {

    @Bean
    public CacheManager<OpenWeatherAPIResponse> cacheManager() {
        return new CacheManager<>();
    }

}
