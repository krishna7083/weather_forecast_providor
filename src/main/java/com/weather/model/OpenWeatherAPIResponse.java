package com.weather.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class OpenWeatherAPIResponse {
    private String cod;
    private int message;
    private int cnt;
    private ArrayList<ForecastList> list;
    private City city;
    private String weatherCondition;
}
