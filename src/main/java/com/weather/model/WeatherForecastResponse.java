package com.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
public class WeatherForecastResponse {
    private String errocode;
    private String errormessage;
    private OpenWeatherAPIResponse apiResponse;
}
