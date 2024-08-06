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
        return new ResponseEntity<>(orderService.makeOrder(orderedItems, appUser, restaurantId), HttpStatus.CREATED);
    }


    @PostMapping("/delete/{orderId}")
    public ResponseEntity<?> makeOrder( @AuthenticationPrincipal AppUser appUser,
                                       @PathVariable() long orderId){
        return new ResponseEntity<>(orderService.deleteOrder(appUser, orderId), HttpStatus.CREATED);
    }


}
