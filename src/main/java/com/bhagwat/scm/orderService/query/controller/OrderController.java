package com.bhagwat.scm.orderService.query.controller;

import com.bhagwat.scm.orderService.query.entity.CustomerOrderDocument;
import com.bhagwat.scm.orderService.query.entity.CommunityOrderDocument;
import com.bhagwat.scm.orderService.query.service.OrderQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderQueryService orderQueryService;

    public OrderController(OrderQueryService orderQueryService) {
        this.orderQueryService = orderQueryService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerOrderDocument>> getCustomerOrders(@PathVariable UUID customerId) {
        return ResponseEntity.ok(orderQueryService.getCustomerOrders(customerId));
    }

    @GetMapping("/customer/{customerId}/{orderId}")
    public ResponseEntity<CustomerOrderDocument> getCustomerOrderById(@PathVariable UUID customerId, @PathVariable UUID orderId) {
        CustomerOrderDocument order = orderQueryService.getCustomerOrderById(orderId);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }

    @GetMapping("/community/{communityId}")
    public ResponseEntity<List<CommunityOrderDocument>> getCommunityOrders(@PathVariable UUID communityId) {
        return ResponseEntity.ok(orderQueryService.getCommunityOrders(communityId));
    }

    @GetMapping("/community/{communityId}/{communityOrderId}")
    public ResponseEntity<CommunityOrderDocument> getCommunityOrderById(@PathVariable UUID communityId,
                                                                          @PathVariable UUID communityOrderId) {
        CommunityOrderDocument order = orderQueryService.getCommunityOrderById(communityOrderId);
        if (order == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(order);
    }
}
