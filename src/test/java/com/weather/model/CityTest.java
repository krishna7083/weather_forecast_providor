package com.weather.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class CityTest {

    @Test
    public void testGetterAndSetter() {
        // Create an instance of City
        City city = new City();

        // Set values using setter methods
        city.setId(1);
        city.setName("New York");

        // Verify values using getter methods
        assertEquals(1, city.getId());
        assertEquals("New York", city.getName());
    }

    @Test
    public void testAllArgsConstructor() {
        // Create an instance of City using the all-args constructor
        City city = new City(1, "New York");

        // Verify values using getter methods
        assertEquals(1, city.getId());
        assertEquals("New York", city.getName());

    }

    @Test
    public void testNoArgsConstructor() {
        // Create an instance of City using the no-args constructor
        City city = new City();

        // Verify default values
        assertEquals(0, city.getId());
        assertNull(city.getName());
    }

    @Test
    public void testEqualsAndHashCode() {
        // Create two instances of City with the same property values
        City city1 = new City(1, "New York");
        City city2 = new City(1, "New York");

        // Verify that the two instances are equal and have the same hash code
        assertEquals(city1, city2);
        assertEquals(city1.hashCode(), city2.hashCode());
    }
}
