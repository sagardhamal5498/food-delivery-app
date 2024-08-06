package com.food_delivery_app.order.entity;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "customer_order")
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "app_user_id")
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Column(name = "order_date")
    private LocalDateTime orderDate;

    @Column(name = "order_status", nullable = false)
    private Boolean orderStatus = false;

    @Column(name = "total_amount")
    private Double totalAmount;






}