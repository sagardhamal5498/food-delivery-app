package com.food_delivery_app.delivery_executive.service;

import com.food_delivery_app.delivery_executive.payload.DeliveryExecutiveDto;
import com.food_delivery_app.order.entity.CustomerOrder;


import java.time.LocalDate;
import java.util.List;

public interface DeliveryExecutiveService {
    DeliveryExecutiveDto addDeliveryExecutive(DeliveryExecutiveDto deliveryExecutiveDto);

    String deleteDeliveryExecutive(long agentId);

    DeliveryExecutiveDto updateDeliveryExecutive(DeliveryExecutiveDto deliveryExecutiveDto,long agentId);

    List<CustomerOrder> ordersExecuteByAgent(long agentId, LocalDate date);
}
