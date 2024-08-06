package com.food_delivery_app.order.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.cart.entity.Cart;
import com.food_delivery_app.cart.repostory.CartRepository;
import com.food_delivery_app.menu.entity.Menu;
import com.food_delivery_app.menu.repository.MenuRepository;
import com.food_delivery_app.order.entity.CustomerOrder;

import com.food_delivery_app.order.enums.OrderStatus;
import com.food_delivery_app.order.exception.MenuNotAvailable;
import com.food_delivery_app.order.exception.OrderNotFound;
import com.food_delivery_app.order.exception.YourCartIsEmpty;
import com.food_delivery_app.order.payload.ItemOrderDto;
import com.food_delivery_app.order.payload.OrderDetailsDto;
import com.food_delivery_app.order.repository.CustomerOrderRepository;
import com.food_delivery_app.restaurant.entity.Restaurant;
import com.food_delivery_app.restaurant.exception.RestaurantNotFound;
import com.food_delivery_app.restaurant.repository.RestaurantRepository;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
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
    public OrderDetailsDto makeOrder(List<ItemOrderDto> orderedItems, AppUser appUser, long restaurantId) {
        if(orderedItems.isEmpty()){
            throw new YourCartIsEmpty("Your cart is empty");
        }
         CustomerOrder order = new CustomerOrder();
         CustomerOrder makedOrder = customerOrderRepository.save(order);
         makedOrder.setAppUser(appUser);
         Restaurant restaurant = restaurantRepository.findById(restaurantId).orElseThrow(
                 ()->new RestaurantNotFound("Restaurant not found")
         );
        makedOrder.setRestaurant(restaurant);
         makedOrder.setOrderDate(LocalDateTime.now());
         for(ItemOrderDto orderItem:orderedItems){
             Cart cart = new Cart();
             cart.setQuantity(orderItem.getQuantity());
             cart.setCustomerOrder(makedOrder);
             Menu menu = menuRepository.findByNameAndRestaurant(orderItem.getItemName(),restaurant).orElseThrow(
                     ()-> new MenuNotAvailable("Requested Menu are not available")
             );
             cart.setMenu(menu);
             cart.setPrice((double) (menu.getPrice()*orderItem.getQuantity()));
             cartRepository.save(cart);
         }
         List<Cart> listOfItemsOrdered = cartRepository.findByCustomerOrder(makedOrder);
         double sum = listOfItemsOrdered.stream().mapToDouble(x -> x.getPrice()).sum();
         makedOrder.setOrderStatus(OrderStatus.PREPARING);
         makedOrder.setTotalAmount(sum);
         CustomerOrder yourOrder = customerOrderRepository.save(makedOrder);
         return orderReturnDto(yourOrder,listOfItemsOrdered);
    }

    @Override
    public String deleteOrder(AppUser appUser, long orderId) {
         CustomerOrder order = customerOrderRepository.findByIdAndAppUser(orderId,appUser).orElseThrow(
                () -> new OrderNotFound("Your order is not found")
        );
         List<Cart> carts = cartRepository.findByCustomerOrder(order);
         cartRepository.deleteAll(carts);
         customerOrderRepository.deleteById(orderId);
        return "Your Ordered Is Deleted";
    }

    private OrderDetailsDto orderReturnDto(CustomerOrder yourOrder,List<Cart> listOfItemsOrdered) {
         OrderDetailsDto order = new OrderDetailsDto();
         order.setOrderId(yourOrder.getId());
         HashMap<String, Long> hm = new HashMap<>();
         for(Cart cart : listOfItemsOrdered){
             hm.put(cart.getMenu().getName(), (long)cart.getQuantity());
         }
         order.setOrderedItems(hm);
         order.setAddress(yourOrder.getAppUser().getAddress());
         order.setOrderedStatus(yourOrder.getOrderStatus().toString());
         order.setRestaurantName(yourOrder.getRestaurant().getName());
         order.setTotalAmount(yourOrder.getTotalAmount());
         order.setOrderDateTime(yourOrder.getOrderDate());
         order.setMobile(yourOrder.getAppUser().getMobile());
        return order;
    }
}
