package com.food_delivery_app.coupon.exception;

import com.food_delivery_app.coupon.entity.Coupon;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionsHandlinng {

    @ExceptionHandler(CouponNotFoundException.class)
    public ResponseEntity<?> couponException(
            CouponNotFoundException couponNotFoundException
    ){
      return new ResponseEntity<>(couponNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
