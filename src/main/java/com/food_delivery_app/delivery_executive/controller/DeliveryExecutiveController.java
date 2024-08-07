package com.food_delivery_app.delivery_executive.controller;
import com.food_delivery_app.delivery_executive.payload.DeliveryExecutiveDto;
import com.food_delivery_app.delivery_executive.service.DeliveryExecutiveService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/food-app/delivery-executive")
public class DeliveryExecutiveController {

private DeliveryExecutiveService deliveryExecutiveService;

    public DeliveryExecutiveController(DeliveryExecutiveService deliveryExecutiveService) {
        this.deliveryExecutiveService = deliveryExecutiveService;
    }
    @PostMapping("/add")
    public ResponseEntity<?> addDeliveryExecutive(@RequestBody DeliveryExecutiveDto deliveryExecutiveDto){
        return new ResponseEntity<>(deliveryExecutiveService.addDeliveryExecutive(deliveryExecutiveDto), HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{agentId}")
    public ResponseEntity<?> deleteDeliveryExecutive(@PathVariable long agentId){
        return new ResponseEntity<>(deliveryExecutiveService.deleteDeliveryExecutive(agentId), HttpStatus.CREATED);
    }

    @PutMapping("/update/{agentId}")
    public ResponseEntity<?> updateDeliveryExecutive(@RequestBody DeliveryExecutiveDto deliveryExecutiveDto,@PathVariable long agentId){
        return new ResponseEntity<>(deliveryExecutiveService.updateDeliveryExecutive(deliveryExecutiveDto,agentId), HttpStatus.CREATED);
    }

    @GetMapping("/{agentId}/orders")
    public ResponseEntity<?> ordersExecuteByAgent(@PathVariable long agentId, @RequestParam LocalDate date){
        System.out.println(date);
        return new ResponseEntity<>(deliveryExecutiveService.ordersExecuteByAgent(agentId,date), HttpStatus.CREATED);
    }






}
