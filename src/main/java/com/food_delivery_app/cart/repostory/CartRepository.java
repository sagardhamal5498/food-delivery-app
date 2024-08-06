package com.food_delivery_app.cart.repostory;

import com.food_delivery_app.cart.entity.Cart;
import com.food_delivery_app.order.entity.CustomerOrder;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {


    List<Cart> findByCustomerOrder(CustomerOrder makedOrder);
}