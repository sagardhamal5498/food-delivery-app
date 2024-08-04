package com.food_delivery_app.restaurant.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class RestaurantDto {

    private String location;
    private String name;
}
