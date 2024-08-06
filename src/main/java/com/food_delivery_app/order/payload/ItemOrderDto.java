package com.food_delivery_app.order.payload;

import lombok.Data;

@Data
public class ItemOrderDto {


    private String itemName;
    private int quantity;
}
