package com.food_delivery_app.menu.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlings {

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<?> categoryException(
            CategoryNotFoundException categoryNotFoundException
    ){
        return new ResponseEntity<>(categoryNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TypeNotFoundException.class)
    public ResponseEntity<?> typeException(
            TypeNotFoundException typeNotFoundException
    ){
        return new ResponseEntity<>(typeNotFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
