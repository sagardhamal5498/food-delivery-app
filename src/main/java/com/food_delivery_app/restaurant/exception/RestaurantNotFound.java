package com.food_delivery_app.restaurant.exception;

public class RestaurantNotFound extends RuntimeException{
    public RestaurantNotFound(String message) {
        super(message);
    }
}
