package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import com.bhagwat.scm.order.entity.CommunityOrderLineDocument;
import com.bhagwat.scm.order.repository.CommunityOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommunityOrderDocumentService {
    private final CommunityOrderRepository repository;

    /**
     * Get aggregated community order by communityId
     */
    public CommunityOrderHeaderDocument getCommunityOrder(String communityId) {
        CommunityOrderHeaderDocument header = repository.findByCommunityId(communityId);
        if (header == null) {
            return null; // or throw exception
        }

        // Aggregate total product quantities at community level
        List<CommunityOrderHeaderDocument.ProductDemand> productDemands = header.getProductDemand();
        for (CommunityOrderHeaderDocument.ProductDemand pd : productDemands) {
            int total = 0;

            for (CommunityOrderLineDocument cluster : header.getCommunityClusters()) {
                if (cluster.getProductId().equals(pd.getProductId())
                        && cluster.getVariantId().equals(pd.getVariantId())) {
                    // Sum customer order quantities
                    int clusterTotal = cluster.getCustomers().stream()
                            .mapToInt(c -> c.getOrderQuantity())
                            .sum();
                    cluster.setTotalClusterQuantity(clusterTotal);
                    total += clusterTotal;
                }
            }

            pd.setTotalQuantity(total);
        }

        return header;
    }
}


