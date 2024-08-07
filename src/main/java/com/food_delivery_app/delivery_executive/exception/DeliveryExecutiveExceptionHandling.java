package com.food_delivery_app.delivery_executive.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DeliveryExecutiveExceptionHandling {
    @ExceptionHandler(MobileNumberIsAlreadyRegistered.class)
    public ResponseEntity<?> mobileNumberIsAlreadyRegistered(MobileNumberIsAlreadyRegistered ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DeliveryAgentNotFound.class)
    public ResponseEntity<?> deliveryAgentNotFound(DeliveryAgentNotFound ex){
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
