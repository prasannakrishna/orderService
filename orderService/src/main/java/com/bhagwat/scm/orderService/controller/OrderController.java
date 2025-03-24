package com.bhagwat.scm.orderService.controller;

import com.bhagwat.scm.orderService.dto.CreateOrderCommand;
import com.bhagwat.scm.orderService.dto.OrderRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CommandGateway commandGateway;

    public OrderController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String createOrder(@RequestBody OrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        commandGateway.send(new CreateOrderCommand(orderId, request.getCustomerId(),
                LocalDateTime.now(), request.getConsignmentId()));
        return orderId;
    }

}
