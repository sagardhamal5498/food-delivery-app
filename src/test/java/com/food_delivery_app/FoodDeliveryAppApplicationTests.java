package com.food_delivery_app;

import com.food_delivery_app.menu.entity.Category;
import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.entity.MenuRepository;
import com.food_delivery_app.menu.entity.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class FoodDeliveryAppApplicationTests {

	@Autowired
	private MenuRepository menuRepository;

	@Test
	void contextLoads() {

		Menu menu=new Menu();

		menu.setCategory(Category.MainCourse);
		menu.setType(Type.Veg);
		menu.setItemname("paneer masala");
		menu.setPrice(200);

		Menu save = menuRepository.save(menu);
		System.out.println(save);
	}

}
