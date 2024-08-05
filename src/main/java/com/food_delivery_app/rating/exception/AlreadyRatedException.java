package com.food_delivery_app.rating.exception;

public class AlreadyRatedException extends RuntimeException{

    public AlreadyRatedException(String message) {
        super(message);
    }
}
