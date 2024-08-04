package com.food_delivery_app.user.exceptions;

public class InvalidDetailsException extends RuntimeException{

    public InvalidDetailsException(String message) {
        super(message);
    }
}
