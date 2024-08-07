package com.food_delivery_app.delivery_executive.exception;

public class DeliveryAgentNotFound extends RuntimeException{
    public DeliveryAgentNotFound(String message) {
        super(message);
    }
}
