package com.food_delivery_app.menu.entity;

import com.food_delivery_app.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "menu")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "itemname", length = 50)
    private String itemname;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(name = "price", nullable = false)
    private Integer price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

//EnumType.STRING: This is the safer and more maintainable option because the actual names of the enum constants are stored. However, it may take more storage space in the database.
    //EnumType.ORDINAL: This uses less space because it stores an integer, but it can lead to data corruption if you change the order of constants in the enum.

}