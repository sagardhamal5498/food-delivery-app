package com.food_delivery_app.order.controller;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.order.entity.CustomerOrder;

import com.food_delivery_app.order.payload.ItemOrderDto;
import com.food_delivery_app.order.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food-app/order")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/make/{restaurantId}")
    public ResponseEntity<?> makeOrder(@RequestBody List<ItemOrderDto> orderedItems, @AuthenticationPrincipal AppUser appUser,
                                       @PathVariable() long restaurantId){

         CustomerOrder order = orderService.makeOrder(orderedItems, appUser, restaurantId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
