package com.food_delivery_app.delivery_executive.entity;

import com.food_delivery_app.order.entity.CustomerOrder;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "delivery_executive")
public class DeliveryExecutive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "mobile_no", length = 15)
    private String mobile;

}