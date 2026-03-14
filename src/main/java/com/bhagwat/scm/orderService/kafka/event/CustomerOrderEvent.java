package com.bhagwat.scm.orderService.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderEvent {
    private String eventType; // CUSTOMER_ORDER_CREATED, CUSTOMER_ORDER_UPDATED, CUSTOMER_ORDER_CANCELLED
    private UUID orderId;
    private UUID customerId;
    private UUID productId;
    private UUID variantId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalAmount;
    private BigDecimal shippingCost;
    private BigDecimal taxAmount;
    private String currency;
    private UUID communityId;
    private UUID sellerId;
    private String inventoryKey;
    private String shippingAddressJson;
    private Instant createdAt;
}
