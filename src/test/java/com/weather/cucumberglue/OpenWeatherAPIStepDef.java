package com.weather.cucumberglue;

import com.weather.model.OpenWeatherAPIResponse;
import com.weather.service.WeatherForecastProviderService;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class OpenWeatherAPIStepDef {

    OpenWeatherAPIResponse openWeatherAPIResponse;

    @Autowired
    WeatherForecastProviderService weatherForecastProviderService;


    @Given("the weather forecast provider is available")
    public void the_weather_forecast_provider_is_available() {
    }

    @When("I request the weather forecast for the city {string} with count {int}")
    public void i_request_the_weather_forecast_for_the_city_with_count(String city, Integer count) {
        openWeatherAPIResponse = weatherForecastProviderService.getWeatherForecast(city,count);
    }
    @Then("the response should have HTTP status code {int}")
    public void the_response_should_have_http_status_code(Integer cod) {
        assertEquals(openWeatherAPIResponse.getCod(),Integer.toString(cod));
    }
    @Then("the response should contain the city {string}")
    public void the_response_should_contain_the_city(String city) {
        assertEquals(openWeatherAPIResponse.getCity().getName(),city);
    }
}
