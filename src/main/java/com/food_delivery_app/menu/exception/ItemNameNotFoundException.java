package com.food_delivery_app.menu.exception;

public class ItemNameNotFoundException extends RuntimeException{

    public ItemNameNotFoundException(String message) {
        super(message);
    }
}
