package com.food_delivery_app.order.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.cart.entity.Cart;
import com.food_delivery_app.cart.repostory.CartRepository;
import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.repository.MenuRepository;
import com.food_delivery_app.order.entity.CustomerOrder;

import com.food_delivery_app.order.payload.ItemOrderDto;
import com.food_delivery_app.order.repository.CustomerOrderRepository;
import com.food_delivery_app.restaurant.repository.RestaurantRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements  OrderService{
    private CustomerOrderRepository customerOrderRepository;
    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private final CartRepository cartRepository;

    public OrderServiceImpl( CustomerOrderRepository customerOrderRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository,
                            CartRepository cartRepository) {
        this.customerOrderRepository = customerOrderRepository;

        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.cartRepository = cartRepository;
    }

    @Override
    public CustomerOrder makeOrder(List<ItemOrderDto> orderedItems, AppUser appUser, long restaurantId) {
         CustomerOrder order = new CustomerOrder();
         CustomerOrder makedOrder = customerOrderRepository.save(order);
         makedOrder.setAppUser(appUser);
         makedOrder.setRestaurant(restaurantRepository.findById(restaurantId).get());
         makedOrder.setOrderDate(LocalDateTime.now());
         for(ItemOrderDto orderItem:orderedItems){
             Cart cart = new Cart();
             cart.setQuantity(orderItem.getQuantity());
             cart.setCustomerOrder(makedOrder);
             Menu menu = menuRepository.findByName(orderItem.getItemName()).get();
             cart.setMenu(menu);
             cart.setPrice((double) (menu.getPrice()*orderItem.getQuantity()));
             cartRepository.save(cart);
         }
         List<Cart> listOfItemsOrdered = cartRepository.findByCustomerOrder(makedOrder);
         double sum = listOfItemsOrdered.stream().mapToDouble(x -> x.getPrice()).sum();
         makedOrder.setOrderStatus(true);
         makedOrder.setTotalAmount(sum);
         CustomerOrder yourOrder = customerOrderRepository.save(makedOrder);
        return yourOrder;
    }
}
