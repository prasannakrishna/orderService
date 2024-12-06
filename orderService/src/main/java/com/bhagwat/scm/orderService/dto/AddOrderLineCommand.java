package com.bhagwat.scm.orderService.dto;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class AddOrderLineCommand {
    @TargetAggregateIdentifier
    private final String orderId;
    private final String lineId;
    private final String skuId;
    private final int orderedQuantity;

    public AddOrderLineCommand(String orderId, String lineId, String skuId, int orderedQuantity) {
        this.orderId = orderId;
        this.lineId = lineId;
        this.skuId = skuId;
        this.orderedQuantity = orderedQuantity;
    }

    // Getters
}

