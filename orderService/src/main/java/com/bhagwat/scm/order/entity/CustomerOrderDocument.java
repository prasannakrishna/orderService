package com.bhagwat.scm.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Document(collection = "customer_orders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrderDocument {
    @Id
    private String orderId;
    private String customerId;
    private String productId;
    private String variantId;
    private String sellerId;
    private String communityId;
    private String inventoryKey;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalAmount;
    private BigDecimal shippingCost;
    private BigDecimal taxAmount;
    private String currency;
    private String orderStatus;
    private Instant eventCreatedAt;
    private Instant savedAt;
}
