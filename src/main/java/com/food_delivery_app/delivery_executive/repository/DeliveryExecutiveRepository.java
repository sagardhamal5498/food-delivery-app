package com.food_delivery_app.delivery_executive.repository;

import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryExecutiveRepository extends JpaRepository<DeliveryExecutive, Long> {
}