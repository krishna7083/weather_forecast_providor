Feature: Weather Forecast Provider

  Scenario: Successfully retrieve weather forecast
    Given the weather forecast provider is available
    When I request the weather forecast for the city "London" with count 5
    Then the response should have HTTP status code 200
    And the response should contain the city "London"