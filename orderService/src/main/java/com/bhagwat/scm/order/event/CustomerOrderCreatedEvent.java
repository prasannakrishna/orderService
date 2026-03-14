package com.bhagwat.scm.order.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * Kafka event published by cartService when a customer checks out their cart.
 * Consumed from topic: customer-order-events
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerOrderCreatedEvent {
    private String eventType;
    private String orderId;
    private String customerId;
    private String productId;
    private String variantId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalAmount;
    private BigDecimal shippingCost;
    private BigDecimal taxAmount;
    private String currency;
    private String communityId;
    private String sellerId;
    private String inventoryKey;
    private Instant createdAt;
}
