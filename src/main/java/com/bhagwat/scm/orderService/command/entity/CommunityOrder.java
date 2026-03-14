package com.bhagwat.scm.orderService.command.entity;

import com.bhagwat.scm.orderService.constant.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "community_orders")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "community_order_id", unique = true, nullable = false)
    private UUID communityOrderId;

    @Column(name = "community_id", nullable = false)
    private UUID communityId;

    @Column(name = "calendar_unit", nullable = false)
    private String calendarUnit;

    @Column(name = "items_json", columnDefinition = "TEXT")
    private String itemsJson;

    @Column(name = "total_amount", precision = 19, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "currency", length = 3)
    private String currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "checkout_date")
    private String checkoutDate;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
}
