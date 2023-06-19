package com.weather.model;

import lombok.*;

import java.util.ArrayList;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ForecastList {
    private int dt;
    private Main main;
    private ArrayList<Weather> weather;
    private Wind wind;
    private String dt_txt;
    private String message;
}
