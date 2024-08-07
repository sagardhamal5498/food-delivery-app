package com.food_delivery_app.delivery_executive.exception;

public class MobileNumberIsAlreadyRegistered extends  RuntimeException{
    public MobileNumberIsAlreadyRegistered(String message) {
        super(message);
    }
}
