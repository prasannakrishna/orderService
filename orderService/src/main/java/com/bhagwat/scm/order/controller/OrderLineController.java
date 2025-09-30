package com.bhagwat.scm.order.controller;

import com.bhagwat.scm.order.dto.OrderLineRequest;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/order-lines")
public class OrderLineController {


    @PostMapping("/{orderId}")
    public String addOrderLine(@PathVariable String orderId, @RequestBody OrderLineRequest request) {
        String lineId = UUID.randomUUID().toString();
        return lineId;
    }
}

