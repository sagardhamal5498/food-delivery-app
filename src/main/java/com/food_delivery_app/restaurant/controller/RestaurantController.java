package com.food_delivery_app.restaurant.controller;
import com.food_delivery_app.restaurant.payload.RestaurantDto;
import com.food_delivery_app.restaurant.service.RestaurantService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food-app/restaurant")
public class RestaurantController {

    private RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> addRestaurant(@Valid @RequestBody RestaurantDto restaurantDto){
        return new ResponseEntity<>(restaurantService.addRestaurant(restaurantDto), HttpStatus.OK);
    }

    @DeleteMapping("/remove")
    public ResponseEntity<?> deleteRestaurant( @RequestParam long  restaurantId){
        return new ResponseEntity<>(restaurantService.removeRestaurant(restaurantId), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchRestaurant(@RequestParam(required = false) String restaurantName ){
        if (restaurantName == null || restaurantName.isEmpty()) {
            return new ResponseEntity<>(restaurantService.getAllRestaurants(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(restaurantService.searchRestaurant(restaurantName), HttpStatus.OK);
        }
    }

    @PutMapping("/update/{restaurantId}")
    public ResponseEntity<?> updateRestaurant( @RequestBody RestaurantDto restaurantDto,@PathVariable long restaurantId){
        return new ResponseEntity<>( restaurantService.updateRestaurant(restaurantDto,restaurantId), HttpStatus.OK);
    }
}
