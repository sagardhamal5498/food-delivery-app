package com.food_delivery_app.coupon.exception;

public class CouponNotFoundException extends RuntimeException{

    public CouponNotFoundException(String message) {
        super(message);
    }
}
