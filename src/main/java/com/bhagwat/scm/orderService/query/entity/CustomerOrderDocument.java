package com.bhagwat.scm.orderService.query.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "customer_orders")
public class CustomerOrderDocument {
    @Id
    private String id;
    private String orderId;
    private String customerId;
    private String productId;
    private String variantId;
    private Integer quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal totalAmount;
    private String currency;
    private String orderStatus;
    private String paymentStatus;
    private String trackingId;
    private Instant createdAt;
    private Instant updatedAt;
}
