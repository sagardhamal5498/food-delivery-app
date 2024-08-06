package com.food_delivery_app.order.exception;

public class MenuNotAvailable extends RuntimeException{
    public MenuNotAvailable(String message) {
        super(message);
    }
}
