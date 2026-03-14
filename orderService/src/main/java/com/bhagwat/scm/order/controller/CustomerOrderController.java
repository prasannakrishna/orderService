package com.bhagwat.scm.order.controller;

import com.bhagwat.scm.order.entity.CustomerOrderHeader;
import com.bhagwat.scm.order.repository.CustomerOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderHeaderRepository customerOrderHeaderRepository;
    private final MongoTemplate mongoTemplate;

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<CustomerOrderHeader>> getOrdersByCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(customerOrderHeaderRepository.findByCustomerId(customerId));
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<CustomerOrderHeader> getOrderById(@PathVariable String orderId) {
        return customerOrderHeaderRepository.findById(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<CustomerOrderHeader> updateStatus(@PathVariable String orderId,
                                                             @RequestParam String status) {
        return customerOrderHeaderRepository.findById(orderId).map(order -> {
            try {
                order.setOrderStatus(com.bhagwat.scm.order.common.OrderStatus.valueOf(status.toUpperCase()));
                return ResponseEntity.ok(customerOrderHeaderRepository.save(order));
            } catch (IllegalArgumentException e) {
                return ResponseEntity.<CustomerOrderHeader>badRequest().build();
            }
        }).orElse(ResponseEntity.notFound().build());
    }
}
