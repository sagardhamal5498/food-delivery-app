package com.food_delivery_app.restaurant.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RestaurantExceptionHandling {

    @ExceptionHandler(RestaurantAlreadyRegistered.class)
    public ResponseEntity<?> restaurantAlreadyRegistered(RestaurantAlreadyRegistered ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
