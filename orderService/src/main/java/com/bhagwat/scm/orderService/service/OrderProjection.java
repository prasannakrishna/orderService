package com.bhagwat.scm.orderService.service;

import com.bhagwat.scm.orderService.dto.OrderCreatedEvent;
import com.bhagwat.scm.orderService.dto.OrderLineAddedEvent;
import com.bhagwat.scm.orderService.entity.Order;
import com.bhagwat.scm.orderService.repository.OrderRepository;
import org.axonframework.eventhandling.EventHandler;

import org.springframework.stereotype.Service;

@Service
public class OrderProjection {

    private final OrderRepository orderRepository;

    public OrderProjection(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();
        order.setOrderId(Long.valueOf(event.getOrderId()));
        order.setCustomerId(event.getCustomerId());
        order.setOrderCreatedDate(event.getOrderCreatedDate());
        orderRepository.save(order);
    }

    @EventHandler
    public void on(OrderLineAddedEvent event) {
        Order order = orderRepository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));
        order.setNumberOfLines(order.getNumberOfLines() + 1);
        orderRepository.save(order);
    }
}
