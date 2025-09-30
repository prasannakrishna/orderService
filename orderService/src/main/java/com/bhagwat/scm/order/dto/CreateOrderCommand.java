package com.bhagwat.scm.order.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateOrderCommand {
    private final String orderId;
    private final String customerId;
    private final LocalDateTime orderCreatedDate;
    private final String consignmentId;

    // Getters
}
