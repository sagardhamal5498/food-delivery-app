package com.food_delivery_app.restaurant.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestaurantDto {


    private long id;

    @NotEmpty(message = "Restaurant name is required")
    @Size(min=3, message = "Restaurant name cannot be less than 3 characters")
    private String name;

    @NotEmpty(message = "Location is required")
    private String location;

    @NotEmpty(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Mobile number is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Mobile number should be valid")
    private String mobile;

    @Min(value = 0, message = "Rating cannot be less than 0")
    @Max(value = 5, message = "Rating cannot be more than 5")
    private Double rating;

}
