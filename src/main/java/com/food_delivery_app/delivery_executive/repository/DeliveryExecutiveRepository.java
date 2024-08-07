package com.food_delivery_app.delivery_executive.repository;

import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DeliveryExecutiveRepository extends JpaRepository<DeliveryExecutive, Long> {
    Optional<DeliveryExecutive> findByMobile(String mobile);
}