package com.food_delivery_app.order.exception;

public class YourCartIsEmpty extends RuntimeException{

    public YourCartIsEmpty(String message) {
        super(message);
    }
}
