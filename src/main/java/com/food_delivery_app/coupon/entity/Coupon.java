package com.food_delivery_app.coupon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "discount_percentage", nullable = false)
    private Double discountPercentage;

    @Column(name = "expiry_time", nullable = false)
    private LocalDateTime expiryTime;

    @Column(name = "code", nullable = false, unique = true)
    private String code;

    // Method to check if the coupon is still valid
    public boolean isValid() {
        return LocalDateTime.now().isBefore(expiryTime);
    }

    // Constructor for creating a 50% discount coupon that expires in 24 hours
    public Coupon(String code) {
        this.discountPercentage = 50.0;
        this.expiryTime = LocalDateTime.now().plusHours(24);
        this.code = code;
    }

    // Default constructor
    public Coupon() {
    }
}
