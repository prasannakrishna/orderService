package com.bhagwat.scm.orderService.query.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "community_orders")
public class CommunityOrderDocument {
    @Id
    private String id;
    private String communityOrderId;
    private String communityId;
    private String calendarUnit;
    private String itemsJson;
    private BigDecimal totalAmount;
    private String currency;
    private String orderStatus;
    private String checkoutDate;
    private Instant createdAt;
}
