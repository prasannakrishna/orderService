package com.bhagwat.scm.orderService.controller;

import com.bhagwat.scm.orderService.dto.AddOrderLineCommand;
import com.bhagwat.scm.orderService.dto.OrderLineRequest;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order-lines")
public class OrderLineController {

    private final CommandGateway commandGateway;

    public OrderLineController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/{orderId}")
    public String addOrderLine(@PathVariable String orderId, @RequestBody OrderLineRequest request) {
        String lineId = UUID.randomUUID().toString();
        commandGateway.send(new AddOrderLineCommand(orderId, lineId, request.getSkuId(), request.getOrderedQuantity()));
        return lineId;
    }
}

