package com.food_delivery_app.menu.repository;

import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Integer> {

    @Query("SELECT m FROM Menu m WHERE m.restaurant=:restaurant")
    List<Menu> findByRestaurant(@Param("restaurant") Restaurant restaurant);

    Optional<Menu> findByName(String itemName);

    @Query("SELECT m FROM Menu m WHERE m.restaurant=:restaurant AND m.name=:itemName")
    Optional<Menu> findByNameAndRestaurant(@Param("itemName") String itemName,@Param("restaurant") Restaurant restaurant);
}