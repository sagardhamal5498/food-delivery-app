package com.food_delivery_app.menu.service;

import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.payload.MenuDto;

import java.util.List;

public interface MenuService {

     MenuDto addItems(MenuDto menuDto, long restaurantId);

     List<MenuDto> getAllItems(long restaurantId);
}
