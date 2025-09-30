package com.bhagwat.scm.order.controller;

import com.bhagwat.scm.order.dto.OrderRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @PostMapping
    public String createOrder(@RequestBody OrderRequest request) {
        String orderId = UUID.randomUUID().toString();
        return orderId;
    }
}
