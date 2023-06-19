package com.weather.exception;


import com.weather.constants.APIConstants;
import com.weather.model.WeatherForecastResponse;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<Object> handleFeignStatusException(FeignException e, HttpServletResponse response) {
        log.error("Exception occured while calling openweather api with exception as {}",e.getMessage());
        return new ResponseEntity<>(WeatherForecastResponse.builder().errocode(APIConstants.ERRORCODE_FAIL)
                .errormessage(e.getMessage()).build(), HttpStatusCode.valueOf(e.status()));
    }
}
