package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.constant.CalendarUnit;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import com.bhagwat.scm.order.entity.CommunityOrderProductLine;
import com.bhagwat.scm.order.repository.CommunityOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommunityOrderAggregationService {

    private final CommunityOrderHeaderRepository headerRepository;

    /**
     * Load header, compute product totals from its customer lines,
     * update totalOrderQuantity for each product line (in-memory).
     */
    @Transactional(readOnly = true)
    public CommunityOrderHeader getAggregatedCommunityOrder(String communityId, CalendarUnit type) {
        CommunityOrderHeader header = headerRepository.findByCommunityIdAndSubscriptionCartType(communityId, type)
                .orElseThrow(() -> new IllegalArgumentException("Community order not found"));

        for (CommunityOrderProductLine productLine : header.getProductLines()) {
            int total = productLine.getCustomerLines().stream()
                    .mapToInt(cl -> cl.getOrderQuantity() == null ? 0 : cl.getOrderQuantity())
                    .sum();
            productLine.setTotalOrderQuantity(total);
            // you may also update aggregated shipped/delivered if needed
        }

        return header;
    }
}