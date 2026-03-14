package com.bhagwat.scm.order.entity;

import com.bhagwat.scm.order.common.OrderStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "customer_order_headers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerOrderHeader {

    @Id
    @Column(name = "order_id", length = 36)
    private String orderId;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "variant_id")
    private String variantId;

    @Column(name = "seller_id")
    private String sellerId;

    @Column(name = "community_id")
    private String communityId;

    @Column(name = "inventory_key")
    private String inventoryKey;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "price_per_unit", precision = 19, scale = 2)
    private BigDecimal pricePerUnit;

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "shipping_cost", precision = 19, scale = 2)
    private BigDecimal shippingCost;

    @Column(name = "tax_amount", precision = 19, scale = 2)
    private BigDecimal taxAmount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    @Builder.Default
    private OrderStatus orderStatus = OrderStatus.CREATED;

    @Column(name = "event_created_at")
    private Instant eventCreatedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Timestamp updatedAt;
}
