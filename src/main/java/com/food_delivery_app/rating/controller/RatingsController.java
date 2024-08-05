package com.food_delivery_app.rating.controller;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.rating.payload.RatingDto;
import com.food_delivery_app.rating.payload.RatingDto2;
import com.food_delivery_app.rating.payload.RatingDto3;
import com.food_delivery_app.rating.service.RatingService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/food-app/ratings")
public class RatingsController {

    private RatingService ratingService;

    public RatingsController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRating(@Valid @RequestBody RatingDto ratingDto
                                       , BindingResult bindingResult
                                       , @AuthenticationPrincipal AppUser appUser
                                       , @RequestParam long restaurantId){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(), HttpStatus.BAD_REQUEST);
        }
        RatingDto2 ratingDto2 = ratingService.addRating(ratingDto, appUser,restaurantId );

        return new ResponseEntity<>(ratingDto2, HttpStatus.CREATED);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> addRating(
              @AuthenticationPrincipal AppUser appUser
            , @RequestParam long restaurantId){

        ratingService.deleteRating(appUser,restaurantId);
        return new ResponseEntity<>("rating deleted successfully!!", HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<?> getallreviews(
            @RequestParam long restaurantId){

        RatingDto3 ratings = ratingService.getallreviews(restaurantId);
        return new ResponseEntity<>(ratings, HttpStatus.CREATED);
    }


}
