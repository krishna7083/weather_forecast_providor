package com.weather.cucumberglue;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features="src/test/resources/features/OpenWeatherAPIIntegrationTest.feature")
public class OpenWeatherAPIRunner {
}
