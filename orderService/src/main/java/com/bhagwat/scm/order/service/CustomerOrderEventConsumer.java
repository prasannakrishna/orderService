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
import org.springframework.kafka.support.Acknowledgment;
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

    /**
     * Idempotent consumer: checks if orderId already exists before saving.
     * PostgreSQL is the source of truth — MongoDB sync failure is non-fatal and
     * does not cause Kafka offset rollback (acknowledged independently).
     *
     * Gap 2 fix: MongoDB write is wrapped in a try/catch. If it fails, PostgreSQL
     * is already committed and the MongoDB document can be backfilled from PostgreSQL
     * later — preventing infinite Kafka retry loops caused by a MongoDB blip.
     */
    @KafkaListener(topics = "customer-order-events", groupId = "order-service",
            containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void consumeCustomerOrder(@Payload CustomerOrderCreatedEvent event) {
        log.info("Received customer-order-events for orderId={} customerId={}", event.getOrderId(), event.getCustomerId());

        // Idempotency check — Kafka may redeliver on consumer restart
        if (customerOrderHeaderRepository.existsById(event.getOrderId())) {
            log.info("Duplicate event ignored: orderId={} already persisted", event.getOrderId());
            return;
        }

        // Write to PostgreSQL (source of truth — inside @Transactional)
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

        // Write to MongoDB (query/read model — outside PostgreSQL transaction scope).
        // MongoDB is eventually consistent with PostgreSQL. A failure here does NOT
        // roll back the PostgreSQL commit or trigger Kafka retry — the document can
        // be replayed/backfilled from PostgreSQL if needed.
        try {
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
        } catch (Exception e) {
            // Non-fatal: PostgreSQL is the source of truth. Log and continue.
            // A separate reconciliation job can sync missed docs from PostgreSQL → MongoDB.
            log.error("MongoDB sync failed for orderId={} — will need manual reconciliation. Cause: {}",
                    event.getOrderId(), e.getMessage());
        }
    }
}
