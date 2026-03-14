package com.bhagwat.scm.orderService.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOrderEvent {
    private String eventType; // COMMUNITY_ORDER_CREATED, COMMUNITY_ORDER_UPDATED
    private UUID communityOrderId;
    private UUID communityId;
    private String calendarUnit; // DAY, WEEK, MONTH, etc.
    private List<CommunityOrderItemEvent> items;
    private BigDecimal totalAmount;
    private String currency;
    private Instant createdAt;
    private String checkoutDate;
}
