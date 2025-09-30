package com.bhagwat.scm.order.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "community_order_header")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommunityOrderHeaderDocument {
    @Id
    private String id;                // MongoDB ID
    private String communityId;       // e.g. 123

    private List<ProductDemand> productDemand; // Aggregated demand for community
    private List<CommunityOrderLineDocument> communityClusters; // Cluster-level breakdown

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProductDemand {
        private String productId;
        private String variantId;
        private int totalQuantity;
    }
}
