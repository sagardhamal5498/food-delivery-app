package com.food_delivery_app.restaurant.service;

import com.food_delivery_app.restaurant.payload.RestaurantDto;

import java.util.List;

public interface RestaurantService {
    RestaurantDto addRestaurant(RestaurantDto restaurantDto);

    String removeRestaurant(long restaurantId);

    RestaurantDto searchRestaurant(String restaurantName);

    RestaurantDto updateRestaurant(RestaurantDto restaurantDto,long restaurantId);

    List<RestaurantDto> getAllRestaurants();
}
