package com.food_delivery_app.rating.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.rating.entity.Ratings;
import com.food_delivery_app.rating.payload.RatingDto;
import com.food_delivery_app.rating.payload.RatingDto2;
import com.food_delivery_app.rating.payload.RatingDto3;

import java.util.List;

public interface RatingService {

    public RatingDto2 addRating(RatingDto ratingDto, AppUser appUser, long restaurantId);

    public void deleteRating(AppUser appUser, long restaurantId);

    RatingDto3 getallreviews(long restaurantId);
}
