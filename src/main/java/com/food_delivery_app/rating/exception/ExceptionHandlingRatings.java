package com.food_delivery_app.rating.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingRatings {

    @ExceptionHandler(AlreadyRatedException.class)
    public ResponseEntity<?> alreadyRatedException(
            AlreadyRatedException aLreadyRatedException
    ){
        return new ResponseEntity<>(aLreadyRatedException.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NoReviewsFoundException.class)
    public ResponseEntity<?> noReviewException(
            NoReviewsFoundException noReviewsFoundException
    ){
        return new ResponseEntity<>(noReviewsFoundException.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
