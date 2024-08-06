package com.food_delivery_app.order.repository;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.order.entity.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByIdAndAppUser(long orderId, AppUser appUser);
}