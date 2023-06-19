package com.weather.service;

import com.weather.api.restclient.OpenWeatherAPIProxy;
import com.weather.cache.CacheManager;
import com.weather.model.OpenWeatherAPIResponse;
import com.weather.service.impl.OpenWeatherForecastProviderImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class OpenWeatherForecastProviderImplTest {

    @Mock
    private OpenWeatherAPIProxy openWeatherAPIProxy;

    @Mock
    private CacheManager<OpenWeatherAPIResponse> cacheManager;

    @Mock
    private WeatherMessagePredictor weatherMessagePredictor;

    @InjectMocks
    private OpenWeatherForecastProviderImpl weatherForecastProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        //weatherForecastProvider = new OpenWeatherForecastProviderImpl(openWeatherAPIProxy, cacheManager);
    }

    @Test
    public void testGetWeatherForecastFromCache() {
        // Mock cache response
        OpenWeatherAPIResponse cachedResponse = new OpenWeatherAPIResponse();
        cachedResponse.setCod("200");
        Mockito.when(cacheManager.get(Mockito.any())).thenReturn(cachedResponse);

        // Call the service method
        OpenWeatherAPIResponse result = weatherForecastProvider.getWeatherForecast("City", 5);

        // Verify cache interaction
        Mockito.verify(cacheManager, Mockito.times(1)).get(Mockito.eq("City"));

        // Assert the result
        assertEquals(cachedResponse, result);
    }

    @Test
    public void testGetWeatherForecastFromAPI() {

        Mockito.when(cacheManager.get(Mockito.any())).thenReturn(null);
        // Mock API response
        OpenWeatherAPIResponse apiResponse = new OpenWeatherAPIResponse();
        apiResponse.setCod("200");
        Mockito.when(openWeatherAPIProxy.getWeatherForecast(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(apiResponse);

        // Call the service method
        OpenWeatherAPIResponse result = weatherForecastProvider.getWeatherForecast("City", 5);

        // Verify cache interaction
        Mockito.verify(cacheManager, Mockito.times(1)).get(Mockito.eq("City"));
        Mockito.verify(cacheManager, Mockito.times(1)).put(Mockito.eq("City"),
                Mockito.eq(apiResponse), Mockito.anyLong());

        // Assert the result
        assertEquals(apiResponse, result);
    }

    @Test
    public void testGetWeatherForecastFromAPINot200() {
        // Mock API response
        OpenWeatherAPIResponse apiResponse = new OpenWeatherAPIResponse();
        apiResponse.setCod("500");
        Mockito.when(openWeatherAPIProxy.getWeatherForecast(Mockito.any(), Mockito.any(), Mockito.anyInt()))
                .thenReturn(apiResponse);

        // Call the service method
        OpenWeatherAPIResponse result = weatherForecastProvider.getWeatherForecast("City", 5);

        // Verify cache interaction
        Mockito.verify(cacheManager, Mockito.times(1)).get(Mockito.eq("City"));
        Mockito.verify(cacheManager, Mockito.times(0)).put(Mockito.anyString(), Mockito.any(), Mockito.anyInt());

        // Assert the result
        assertNull(result.getList());
    }
}

