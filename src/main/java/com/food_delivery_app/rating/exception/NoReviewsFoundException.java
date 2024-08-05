package com.food_delivery_app.rating.exception;

public class NoReviewsFoundException extends RuntimeException{

    public NoReviewsFoundException(String message) {
        super(message);
    }
}
