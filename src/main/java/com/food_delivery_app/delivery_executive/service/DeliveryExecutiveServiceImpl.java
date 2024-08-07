package com.food_delivery_app.delivery_executive.service;
import com.food_delivery_app.delivery_executive.entity.DeliveryExecutive;
import com.food_delivery_app.delivery_executive.exception.DeliveryAgentNotFound;
import com.food_delivery_app.delivery_executive.exception.MobileNumberIsAlreadyRegistered;
import com.food_delivery_app.delivery_executive.payload.DeliveryExecutiveDto;
import com.food_delivery_app.delivery_executive.repository.DeliveryExecutiveRepository;
import com.food_delivery_app.order.entity.CustomerOrder;
import com.food_delivery_app.order.repository.CustomerOrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DeliveryExecutiveServiceImpl implements DeliveryExecutiveService {

    private DeliveryExecutiveRepository deliveryExecutiveRepository;
    private CustomerOrderRepository orderRepository;

    public DeliveryExecutiveServiceImpl(DeliveryExecutiveRepository deliveryExecutiveRepository, CustomerOrderRepository orderRepository) {
        this.deliveryExecutiveRepository = deliveryExecutiveRepository;
        this.orderRepository = orderRepository;
    }


    @Override
    public DeliveryExecutiveDto addDeliveryExecutive(DeliveryExecutiveDto deliveryExecutiveDto) {

         Optional<DeliveryExecutive> opDeliveryExecutive = deliveryExecutiveRepository.findByMobile(deliveryExecutiveDto.getMobile());
         if(opDeliveryExecutive.isPresent()){
             throw new MobileNumberIsAlreadyRegistered("This mobile number "+ deliveryExecutiveDto.getMobile() + " is already registered.");
         }
         DeliveryExecutive deliveryExecutive = new DeliveryExecutive();
         deliveryExecutive.setName(deliveryExecutiveDto.getName());
         deliveryExecutive.setMobile(deliveryExecutiveDto.getMobile());
         deliveryExecutive.setAddress(deliveryExecutiveDto.getAddress());
        return DeliveryExecutiveEntityToDto(deliveryExecutiveRepository.save(deliveryExecutive));

    }

    @Override
    public String deleteDeliveryExecutive(long agentId) {
         Optional<DeliveryExecutive> opAgent = deliveryExecutiveRepository.findById(agentId);
         if(opAgent.isEmpty()){
             throw new DeliveryAgentNotFound("Delivery agent for id "+ agentId + " not found.");
         }
         deliveryExecutiveRepository.deleteById(agentId);
        return "Record is deleted Successfully";
    }

    @Override
    public DeliveryExecutiveDto updateDeliveryExecutive(DeliveryExecutiveDto deliveryExecutiveDto,long agentId) {
         Optional<DeliveryExecutive> opAgent = deliveryExecutiveRepository.findById(agentId);
         if(opAgent.isPresent()){
              DeliveryExecutive deliveryExecutive = opAgent.get();
              deliveryExecutive.setMobile(deliveryExecutiveDto.getMobile()!=null &&
                      !deliveryExecutiveDto.getMobile().isEmpty()?deliveryExecutiveDto.getMobile(): deliveryExecutive.getMobile());
              deliveryExecutive.setAddress(deliveryExecutiveDto.getAddress()!=null &&
                     !deliveryExecutiveDto.getAddress().isEmpty()?deliveryExecutiveDto.getAddress(): deliveryExecutive.getAddress());
             deliveryExecutive.setName(deliveryExecutiveDto.getName()!=null &&
                     !deliveryExecutiveDto.getName().isEmpty()?deliveryExecutiveDto.getName(): deliveryExecutive.getName());

            return DeliveryExecutiveEntityToDto(deliveryExecutiveRepository.save(deliveryExecutive));
         }else {
             throw new DeliveryAgentNotFound("Delivery Executive not found");
         }

    }

    @Override
    public List<CustomerOrder> ordersExecuteByAgent(long agentId, LocalDate date) {
         DeliveryExecutive deliveryExecutive = deliveryExecutiveRepository.findById(agentId).orElseThrow(
                () -> new DeliveryAgentNotFound("Delivery agent not found for id " + agentId)
        );
         List<CustomerOrder> listOfAllOrders = orderRepository.findByDeliveryExecutive(deliveryExecutive);
         List<CustomerOrder> collect = listOfAllOrders.stream().filter(order -> order.getOrderDate().toLocalDate().equals(date)).collect(Collectors.toList());
         return collect;
    }

    private DeliveryExecutiveDto DeliveryExecutiveEntityToDto(DeliveryExecutive savedAgent) {
        DeliveryExecutiveDto deliveryExecutive = new DeliveryExecutiveDto();
        deliveryExecutive.setId(savedAgent.getId());
        deliveryExecutive.setName(savedAgent.getName());
        deliveryExecutive.setMobile(savedAgent.getMobile());
        deliveryExecutive.setAddress(savedAgent.getAddress());
        return deliveryExecutive;
    }
}

