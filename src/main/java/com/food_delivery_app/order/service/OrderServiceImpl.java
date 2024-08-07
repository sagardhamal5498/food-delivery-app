package com.food_delivery_app.order.service;

import com.food_delivery_app.appuser.entity.AppUser;
import com.food_delivery_app.cart.entity.Cart;
import com.food_delivery_app.cart.repostory.CartRepository;
import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import com.food_delivery_app.delivery_executive.repository.DeliveryExecutiveRepository;
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
import jakarta.persistence.criteria.Order;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements  OrderService{
    private CustomerOrderRepository customerOrderRepository;
    private RestaurantRepository restaurantRepository;
    private MenuRepository menuRepository;
    private final CartRepository cartRepository;
    private DeliveryExecutiveRepository deliveryExecutiveRepository;
    private final Map<Long, LocalDateTime> newOrdersMap = new ConcurrentHashMap<>();

    public OrderServiceImpl(CustomerOrderRepository customerOrderRepository, RestaurantRepository restaurantRepository, MenuRepository menuRepository,
                            CartRepository cartRepository, DeliveryExecutiveRepository deliveryExecutiveRepository) {
        this.customerOrderRepository = customerOrderRepository;

        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.cartRepository = cartRepository;
        this.deliveryExecutiveRepository = deliveryExecutiveRepository;
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
             cart.setCustomerOrder(makedOrder);
             Menu menu = menuRepository.findByRestaurantAndItemname(restaurant, orderItem.getItemName()).orElseThrow(
                     ()-> new MenuNotAvailable("Requested Menu are not available")
             );
             cart.setMenu(menu);
             cart.setQuantity(orderItem.getQuantity());
             cart.setPrice((double) (menu.getPrice()*orderItem.getQuantity()));
             cartRepository.save(cart);
         }
         List<Cart> listOfItemsOrdered = cartRepository.findByCustomerOrder(makedOrder);
         double sum = listOfItemsOrdered.stream().mapToDouble(x -> x.getPrice()).sum();
         makedOrder.setOrderStatus(OrderStatus.PREPARING);
         makedOrder.setTotalAmount(sum);
         List<DeliveryExecutive> executiveList = deliveryExecutiveRepository.findAll();
        long randomId = selectRandomId(executiveList);
        DeliveryExecutive deliveryExecutive = deliveryExecutiveRepository.findById(randomId).get();
        makedOrder.setDeliveryExecutive(deliveryExecutive);
        CustomerOrder yourOrder = customerOrderRepository.save(makedOrder);
        newOrdersMap.put(yourOrder.getId(), yourOrder.getOrderDate());

        return orderReturnDto(yourOrder,listOfItemsOrdered);
    }

    @Scheduled(fixedRate = 60000)
    public void updateOrderStatuses() {
        newOrdersMap.forEach((orderId, creationTime) -> {
            LocalDateTime now = LocalDateTime.now();
            if (creationTime.plusMinutes(15).isBefore(now)) {
                updateOrderStatus(orderId, OrderStatus.OUT_FOR_DELIVERY);
                newOrdersMap.put(orderId, now);
            } else if (creationTime.plusMinutes(30).isBefore(now)) {
                updateOrderStatus(orderId, OrderStatus.DELIVERD);
                newOrdersMap.remove(orderId);
            }
        });
    }

    public void updateOrderStatus(long orderId, OrderStatus newStatus) {
        CustomerOrder order = customerOrderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFound("Order not found"));
        order.setOrderStatus(newStatus);
        order.setOrderDate(LocalDateTime.now());
        customerOrderRepository.save(order);
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
             hm.put(cart.getMenu().getItemname(), (long)cart.getQuantity());
         }
         order.setOrderedItems(hm);
         order.setAddress(yourOrder.getAppUser().getAddress());
         order.setOrderedStatus(yourOrder.getOrderStatus().toString());
         order.setRestaurantName(yourOrder.getRestaurant().getName());
         order.setTotalAmount(yourOrder.getTotalAmount());
         order.setOrderDateTime(yourOrder.getOrderDate());
         order.setMobile(yourOrder.getAppUser().getMobile());
         order.setDeliveryExecutiveName(yourOrder.getDeliveryExecutive().getName());
         order.setDeliveryExecutiveContactNo(yourOrder.getDeliveryExecutive().getMobile());

        return order;
    }

    private static long selectRandomId(List<DeliveryExecutive> executiveList) {

        List<Long> listId = executiveList.stream().map(x -> x.getId()).collect(Collectors.toList());

        if (listId.isEmpty()) {
            throw new IllegalStateException("No Delivery Executives found");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(listId.size());
        return listId.get(randomIndex);
    }
}
