package com.food_delivery_app.order.entity;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.coupon.entity.Coupon;
import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import com.food_delivery_app.order.enums.OrderStatus;
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

    @Column(name = "order_status")
    private OrderStatus orderStatus ;

    @Column(name = "total_amount")
    private Double totalAmount;

    @ManyToOne
    @JoinColumn(name = "delivery_executive_id")
    private DeliveryExecutive deliveryExecutive;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    // Method to apply the coupon to the total amount
    public void applyCoupon(Coupon coupon) {
        if (coupon != null && coupon.isValid()) {
            this.totalAmount = this.totalAmount * (1 - coupon.getDiscountPercentage() / 100);
            this.coupon = coupon;
        }
    }

    // Method to finalize the order without any coupon
    public void finalizeOrderWithoutCoupon() {
        // No discount applied, just set the totalAmount as is
        this.coupon = null;
    }

}