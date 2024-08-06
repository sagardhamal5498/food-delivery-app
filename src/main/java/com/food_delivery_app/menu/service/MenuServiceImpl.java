package com.food_delivery_app.menu.service;

import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.repository.MenuRepository;
import com.food_delivery_app.menu.payload.MenuDto;
import com.food_delivery_app.restaurant.entity.Restaurant;
import com.food_delivery_app.restaurant.exception.RestaurantNotFound;
import com.food_delivery_app.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService{

    private MenuRepository menuRepository;
    private RestaurantRepository restaurantRepository;

    public MenuServiceImpl(MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Override
    public MenuDto addItems(MenuDto menuDto ,long restaurantId) {

        Optional<Restaurant> optional = restaurantRepository.findById(restaurantId);
        if(optional.isPresent()){
            Restaurant restaurant = optional.get();

            Menu savedMenu = dtoToEntity(menuDto, restaurant);
            MenuDto menuDto1 = entityToDto(savedMenu);
            return menuDto1;
        }
        else{
            throw new RestaurantNotFound("Restaurant not found!!!");
        }
    }

    @Override
    public List<MenuDto> getAllItems(long restaurantId) {
        Optional<Restaurant> optional = restaurantRepository.findById(restaurantId);
        if(optional.isPresent()){
            Restaurant restaurant = optional.get();
            List<Menu> menuList = menuRepository.findByRestaurant(restaurant);
            List<MenuDto> collect = menuList.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
            return collect;
        }
        else {
            throw new RestaurantNotFound("Restaurant not found!!!");
        }
    }

    Menu dtoToEntity(MenuDto menuDto ,Restaurant restaurant){

        Menu menu1=new Menu();

//        if(menuDto.getCategory()==null){
//            throw new CategoryNotFoundException("Category not Found !! "+"\n"+"It should be MainCourse/Starter/Bread/Chinese");
//        }
//        else if(menuDto.getType()==null){
//            throw new TypeNotFoundException("Type not Found !! "+"\n"+"It should be Veg/NonVeg");
//        }
        menu1.setCategory(menuDto.getCategory());
        menu1.setItemname(menuDto.getItemname());
        menu1.setType(menuDto.getType());
        menu1.setPrice(menuDto.getPrice());
        menu1.setRestaurant(restaurant);

        Menu savedMenu = menuRepository.save(menu1);
        return savedMenu;
    }

    MenuDto entityToDto(Menu menu){

        MenuDto menuDto=new MenuDto();

        menuDto.setItemname(menu.getItemname());
        menuDto.setCategory(menu.getCategory());
        menuDto.setType(menu.getType());
        menuDto.setRestaurantname(menu.getRestaurant().getName());
        menuDto.setPrice(menu.getPrice());
        return menuDto;
    }
}
