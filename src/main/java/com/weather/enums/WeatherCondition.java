package com.weather.enums;

public enum WeatherCondition {
    RAIN("Carry an umbrella."),
    HIGH_TEMPERATURE("Use sunscreen lotion."),
    HIGH_WINDS("It’s too windy, watch out!"),
    THUNDERSTORM("Don’t step out! A storm is brewing!");

    private final String message;

    WeatherCondition(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
