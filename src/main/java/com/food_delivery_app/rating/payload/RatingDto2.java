package com.food_delivery_app.rating.payload;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RatingDto2 {

    private double rating;

    private String description;

    private String restaurantName;
}
