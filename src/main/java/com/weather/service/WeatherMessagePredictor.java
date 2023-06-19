package com.weather.service;

import com.weather.enums.WeatherCondition;
import com.weather.model.ForecastList;
import com.weather.model.OpenWeatherAPIResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@Slf4j
public class WeatherMessagePredictor {

    private static final int DAYS=3;
    private static final double TEMPCRITERIA = 40.0;
    private static final double WINDCRITERIA = 10.0;
    private static final String THUNDERSTOMES = "Thunderstorm";
    private static final String RAIN = "Rain";

    public String calculateWeatherMessage(OpenWeatherAPIResponse openWeatherAPIResponse) {
        String message = conditionCalculator(openWeatherAPIResponse.getList(),DAYS);
        return message;
    }

    private String conditionCalculator(ArrayList<ForecastList> forecastLists,int days) {
        String predictorMessage ="";
        for(int i=0; i< forecastLists.size() && i<days; i++){
            ForecastList forecastList = forecastLists.get(i);
            if(forecastList.getWeather().get(0).getMain().equalsIgnoreCase(RAIN)){
                log.debug("weather condition for the date {} is {}",forecastList.getDt_txt(),
                        WeatherCondition.RAIN.getMessage());
                predictorMessage = WeatherCondition.RAIN.getMessage();
                break;
            }
            if(convertKelvinToCelsius(forecastList.getMain().getTemp())>TEMPCRITERIA){
                log.debug("weather condition for the date {} is {}",forecastList.getDt_txt(),
                        WeatherCondition.HIGH_TEMPERATURE.getMessage());
                predictorMessage = WeatherCondition.HIGH_TEMPERATURE.getMessage();
                break;
            }
            if(convertMetersPerSecondToMetersPerHour(forecastList.getWind().getSpeed())>WINDCRITERIA){
                log.debug("weather condition for the date {} is {}",forecastList.getDt_txt(),
                        WeatherCondition.HIGH_WINDS.getMessage());
                predictorMessage = WeatherCondition.HIGH_WINDS.getMessage();
                break;
            }
            if(forecastList.getWeather().get(0).getMain().equalsIgnoreCase(THUNDERSTOMES)){
                log.debug("weather condition for the date {} is {}",forecastList.getDt_txt(),
                        WeatherCondition.THUNDERSTORM.getMessage());
                predictorMessage = WeatherCondition.THUNDERSTORM.getMessage();
                break;
            }
        }
        return predictorMessage;
    }

    private double convertKelvinToCelsius(double kelvin) {
        return kelvin - 273.15;
    }

    private double convertMetersPerSecondToMetersPerHour(double speed) {
        return speed * 3.6;
    }
}
