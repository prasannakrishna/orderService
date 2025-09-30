package com.bhagwat.scm.order.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOrderLineDocument {
    private String communityClusterId;   // Unique for community + cluster
    private String clusterId;
    private String productId;
    private String variantId;
    private int totalClusterQuantity;

    private List<CustomerOrder> customers; // Customer-level demand

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CustomerOrder {
        private String customerId;
        private String productId;
        private String variantId;
        private int orderQuantity;
    }
}
