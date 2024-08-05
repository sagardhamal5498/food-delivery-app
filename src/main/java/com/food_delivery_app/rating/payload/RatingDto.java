package com.food_delivery_app.rating.payload;

import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

@Data
public class RatingDto {

    @Range(min = 0, max = 5 ,message = "Rating should be between 0-5 ")
    private double rating;

    @Size(min = 10, max = 200 ,message = "should be atleast 10 characters!!")
    private String description;

}
