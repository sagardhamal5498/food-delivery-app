package com.food_delivery_app.menu.payload;

import com.food_delivery_app.menu.entity.Category;
import com.food_delivery_app.menu.entity.Type;
import lombok.Data;

@Data
public class MenuDto {

    private String itemname;
    private Category category;
    private Type type;
    private int price;
    private String restaurantname;
}
