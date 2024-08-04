package com.food_delivery_app.restaurant.service;

import com.food_delivery_app.restaurant.entity.Restaurant;
import com.food_delivery_app.restaurant.exception.RestaurantAlreadyRegistered;
import com.food_delivery_app.restaurant.exception.RestaurantNotFound;
import com.food_delivery_app.restaurant.payload.RestaurantDto;
import com.food_delivery_app.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RestaurantServiceImpl implements RestaurantService{
    private RestaurantRepository restaurantRepository;

    public RestaurantServiceImpl(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public RestaurantDto addRestaurant(RestaurantDto restaurantDto) {
         Optional<Restaurant> optionalRestaurant = restaurantRepository.findByName(restaurantDto.getName());
         if(optionalRestaurant.isPresent()) {
         throw new RestaurantAlreadyRegistered("Restaurant Already Registered");
         }
        Restaurant restaurant = restaurantDtoToEntity(restaurantDto);
         Restaurant savedRestaurant = restaurantRepository.save(restaurant);
         return restaurantEntityToDto(savedRestaurant);
    }

    @Override
    public String removeRestaurant(long restaurantId) {
         Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantNotFound("Restaurant id " + restaurantId + " not found.")
        );
         restaurantRepository.deleteById(restaurant.getId());
        return "Restaurant is deleted successfully.";
    }

    @Override
    public RestaurantDto searchRestaurant(String restaurantName) {
             Optional<Restaurant>  opRestaurant = restaurantRepository.findByName(restaurantName);
             if(opRestaurant.isPresent()){
                  Restaurant restaurant = opRestaurant.get();
                 return restaurantEntityToDto(restaurant);
             }else{
                 throw new RestaurantNotFound("Restaurant not found");
             }
    }

    @Override
    public RestaurantDto updateRestaurant(RestaurantDto restaurantDto,long restaurantId) {

         Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                () -> new RestaurantNotFound("Restaurant id " + restaurantId + "  not found.")
        );
         restaurant.setMobile(restaurantDto.getMobile());
         restaurant.setLocation(restaurantDto.getLocation());
         restaurant.setEmail(restaurantDto.getEmail());
       return restaurantEntityToDto(restaurantRepository.save(restaurant));

    }

    @Override
    public List<RestaurantDto> getAllRestaurants() {
        List<Restaurant> listOfRestaurant = restaurantRepository.findAll();
        return listOfRestaurant.stream().map(this::restaurantEntityToDto).collect(Collectors.toList());

    }

    private RestaurantDto restaurantEntityToDto(Restaurant savedRestaurant) {
        RestaurantDto restaurant = new RestaurantDto();
        restaurant.setId(savedRestaurant.getId());
        restaurant.setName(savedRestaurant.getName());
        restaurant.setRating(savedRestaurant.getRating());
        restaurant.setMobile(savedRestaurant.getMobile());
        restaurant.setEmail(savedRestaurant.getEmail());
        restaurant.setEmail(savedRestaurant.getEmail());
        restaurant.setLocation(savedRestaurant.getLocation());
        return restaurant;
    }

    public Restaurant restaurantDtoToEntity(RestaurantDto restaurantDto) {
         Restaurant restaurant = new Restaurant();
         restaurant.setName(restaurantDto.getName());
         restaurant.setRating(restaurantDto.getRating());
         restaurant.setMobile(restaurantDto.getMobile());
         restaurant.setEmail(restaurant.getEmail());
         restaurant.setEmail(restaurantDto.getEmail());
         restaurant.setLocation(restaurantDto.getLocation());
        return restaurant;
    }
}
