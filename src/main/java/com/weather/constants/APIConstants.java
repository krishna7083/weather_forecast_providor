package com.weather.constants;

import org.springframework.web.bind.annotation.RequestParam;

public class APIConstants {

    private APIConstants() {
        throw new IllegalStateException("APIConstants class");
    }

    public static final String OPENWEATHERAPI="openweather-api";
    public static final String Q = "q";
    public static final String APIKEY="apiKey";
    public static final String COUNT = "count";
    public static final String VERSION = "/data/2.5/forecast";
    public static final String RETRIEVE_FORECAST = "retrieve-forecast";
    public static final String ERRORCODE_SUCCESS = "00";
    public static final String ERRORCODE_FAIL = "01";
    public static final String SUCCESS = "SUCCESS";
}
