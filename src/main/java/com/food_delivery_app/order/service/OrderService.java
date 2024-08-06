package com.food_delivery_app.order.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.order.entity.CustomerOrder;
import com.food_delivery_app.order.payload.ItemOrderDto;
import com.food_delivery_app.order.payload.OrderDetailsDto;

import java.util.List;

public interface OrderService {
    OrderDetailsDto makeOrder(List<ItemOrderDto> orderedItems, AppUser appUser, long restaurantId);

    String deleteOrder(AppUser appUser, long orderId);
}
