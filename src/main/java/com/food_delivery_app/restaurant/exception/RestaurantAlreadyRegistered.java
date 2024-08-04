package com.food_delivery_app.restaurant.exception;

public class RestaurantAlreadyRegistered extends RuntimeException{
    public RestaurantAlreadyRegistered(String message) {
        super(message);
    }
}
