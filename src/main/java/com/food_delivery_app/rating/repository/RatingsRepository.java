package com.food_delivery_app.rating.repository;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.rating.entity.Ratings;
import com.food_delivery_app.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface RatingsRepository extends JpaRepository<Ratings, Integer> {

    @Query("SELECT r FROM Ratings r where r.restaurant=:restaurant AND r.appUser=:appuser")
    Optional<Ratings> findByRestaurantAndUsername(@Param("restaurant") Restaurant restaurant,@Param("appuser") AppUser appUser);

    @Query("SELECT r FROM Ratings r where r.restaurant=:restaurant")
    List<Ratings> findReviewsByRestaurant(@Param("restaurant") Restaurant restaurant);
}