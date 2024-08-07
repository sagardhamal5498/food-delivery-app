package com.food_delivery_app.order.repository;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import com.food_delivery_app.order.entity.CustomerOrder;
import jakarta.persistence.criteria.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
    Optional<CustomerOrder> findByIdAndAppUser(long orderId, AppUser appUser);

    List<CustomerOrder> findByDeliveryExecutive(DeliveryExecutive deliveryExecutive);
}