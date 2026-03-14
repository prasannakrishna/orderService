package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.common.OrderStatus;
import com.bhagwat.scm.order.entity.CustomerOrderDocument;
import com.bhagwat.scm.order.entity.CustomerOrderHeader;
import com.bhagwat.scm.order.event.CustomerOrderCreatedEvent;
import com.bhagwat.scm.order.repository.CustomerOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOrderEventConsumer {

    private final CustomerOrderHeaderRepository customerOrderHeaderRepository;
    private final MongoTemplate mongoTemplate;

    @KafkaListener(topics = "customer-order-events", groupId = "order-service",
            containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void consumeCustomerOrder(@Payload CustomerOrderCreatedEvent event) {
        log.info("Received customer-order-events for orderId={} customerId={}", event.getOrderId(), event.getCustomerId());

        // Write to PostgreSQL (command/write model)
        CustomerOrderHeader header = CustomerOrderHeader.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productId(event.getProductId())
                .variantId(event.getVariantId())
                .sellerId(event.getSellerId())
                .communityId(event.getCommunityId())
                .inventoryKey(event.getInventoryKey())
                .quantity(event.getQuantity() != null ? event.getQuantity() : 0)
                .pricePerUnit(event.getPricePerUnit())
                .totalAmount(event.getTotalAmount())
                .shippingCost(event.getShippingCost())
                .taxAmount(event.getTaxAmount())
                .currency(event.getCurrency())
                .orderStatus(OrderStatus.CREATED)
                .eventCreatedAt(event.getCreatedAt())
                .build();

        customerOrderHeaderRepository.save(header);
        log.info("Saved CustomerOrderHeader to PostgreSQL for orderId={}", event.getOrderId());

        // Write to MongoDB (query/read model)
        CustomerOrderDocument doc = CustomerOrderDocument.builder()
                .orderId(event.getOrderId())
                .customerId(event.getCustomerId())
                .productId(event.getProductId())
                .variantId(event.getVariantId())
                .sellerId(event.getSellerId())
                .communityId(event.getCommunityId())
                .inventoryKey(event.getInventoryKey())
                .quantity(event.getQuantity() != null ? event.getQuantity() : 0)
                .pricePerUnit(event.getPricePerUnit())
                .totalAmount(event.getTotalAmount())
                .shippingCost(event.getShippingCost())
                .taxAmount(event.getTaxAmount())
                .currency(event.getCurrency())
                .orderStatus(OrderStatus.CREATED.name())
                .eventCreatedAt(event.getCreatedAt())
                .savedAt(Instant.now())
                .build();

        mongoTemplate.save(doc, "customer_orders");
        log.info("Saved CustomerOrderDocument to MongoDB for orderId={}", event.getOrderId());
    }
}
