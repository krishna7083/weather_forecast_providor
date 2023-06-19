package com.weather.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Weather{
    private int id;
    private String main;
    private String description;
    private String icon;
}
