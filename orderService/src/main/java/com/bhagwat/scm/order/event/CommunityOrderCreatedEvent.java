package com.bhagwat.scm.order.event;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Kafka event published by cartService when a community subscription checkout is triggered.
 * Consumed from topic: community-order-events
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityOrderCreatedEvent {
    private String eventType;
    private String communityOrderId;
    private String communityId;
    private String calendarUnit;
    private List<CommunityOrderItemDto> items;
    private BigDecimal totalAmount;
    private String currency;
    private String checkoutDate;
    private Instant createdAt;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class CommunityOrderItemDto {
        private String productId;
        private String variantId;
        private String sellerId;
        private Integer quantity;
        private BigDecimal price;
    }
}
