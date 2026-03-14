package com.bhagwat.scm.orderService.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOrderItemEvent {
    private UUID productId;
    private UUID variantId;
    private UUID sellerId;
    private Integer totalQuantity;
    private BigDecimal pricePerUnit;
}
