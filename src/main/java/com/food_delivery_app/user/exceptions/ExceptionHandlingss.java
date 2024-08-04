package com.food_delivery_app.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingss {


    @ExceptionHandler(InvalidDetailsException.class)
    public ResponseEntity<?> invalidexception(
            InvalidDetailsException exception
    ){

        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
