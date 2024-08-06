package com.food_delivery_app.menu.controller;

import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.payload.MenuDto;
import com.food_delivery_app.menu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/food-app/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @PostMapping("/add")
    public ResponseEntity<?> addMenu(@RequestBody MenuDto menuDto, @RequestParam long restaurantId){

        MenuDto savedMenu = menuService.addItems(menuDto ,restaurantId);
        return new ResponseEntity<>(savedMenu, HttpStatus.CREATED);
    }

    @GetMapping("/search/{restaurantId}")
    public ResponseEntity<?> getAllMenu(@PathVariable long restaurantId){

        List<MenuDto> menuList = menuService.getAllItems(restaurantId);
        return new ResponseEntity<>(menuList, HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateMenuPrice(
            @RequestParam long restaurantId, @RequestParam String itemname,@RequestParam int newprice){

        MenuDto menuDto = menuService.updatePrice(restaurantId, itemname, newprice);
        return new ResponseEntity<>(menuDto, HttpStatus.OK);
    }
}
