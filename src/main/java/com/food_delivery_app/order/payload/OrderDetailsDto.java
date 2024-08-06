package com.food_delivery_app.order.payload;
import com.food_delivery_app.cart.entity.Cart;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class OrderDetailsDto {


    private long orderId;
    private Map<String,Long> orderedItems;
    private LocalDateTime orderDateTime;
    private String Mobile;
    private String address;
    private String restaurantName;
    private Double totalAmount;
    private String orderedStatus;

}
