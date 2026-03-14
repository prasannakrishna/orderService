package com.bhagwat.scm.orderService.command.entity;

import com.bhagwat.scm.orderService.constant.OrderStatus;
import com.bhagwat.scm.orderService.constant.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "customer_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "order_id", unique = true, nullable = false)
    private UUID orderId;

    @Column(name = "customer_id", nullable = false)
    private UUID customerId;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "variant_id")
    private UUID variantId;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

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

    @Column(name = "community_id")
    private UUID communityId;

    @Column(name = "seller_id")
    private UUID sellerId;

    @Column(name = "inventory_key")
    private String inventoryKey;

    @Column(name = "shipping_address_json", columnDefinition = "TEXT")
    private String shippingAddressJson;

    @Column(name = "tracking_id")
    private String trackingId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_status", nullable = false)
    private PaymentStatus paymentStatus;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
