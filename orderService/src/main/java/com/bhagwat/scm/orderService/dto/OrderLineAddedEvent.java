package com.bhagwat.scm.orderService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@Builder
public class OrderLineAddedEvent {
    private final String orderId;
    private final String lineId;
    private final String skuId;
    private final int orderedQuantity;

    public OrderLineAddedEvent(String orderId, String lineId, String skuId, int orderedQuantity) {
        this.orderId = orderId;
        this.lineId = lineId;
        this.skuId = skuId;
        this.orderedQuantity = orderedQuantity;
    }

    // Getters
}

