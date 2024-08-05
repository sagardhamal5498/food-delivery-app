package com.food_delivery_app.rating.payload;

import lombok.Data;

import java.util.List;

@Data
public class RatingDto3 {

    private String restaurantName;

    private List<RatingDto4> ratinglist;
}
