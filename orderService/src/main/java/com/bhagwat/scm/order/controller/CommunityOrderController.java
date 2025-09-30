package com.bhagwat.scm.order.controller;

import com.bhagwat.scm.order.constant.CalendarUnit;
import com.bhagwat.scm.order.dto.CommunityOrderRequest;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import com.bhagwat.scm.order.service.CommunityOrderAggregationService;
import com.bhagwat.scm.order.service.CommunityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/community-orders")
public class CommunityOrderController {

    private final CommunityOrderService communityOrderService;
    private final CommunityOrderAggregationService aggregationService;

    @Autowired
    public CommunityOrderController(CommunityOrderService communityOrderService, CommunityOrderAggregationService aggregationService) {
        this.communityOrderService = communityOrderService;
        this.aggregationService = aggregationService;
    }

    /**
     * Creates or updates a community order based on the composite key (communityId, cartType).
     * If the order exists, it updates existing lines or adds new ones.
     *
     * @param request The request body containing the order details.
     * @return A ResponseEntity with the created/updated order header.
     */
    @PostMapping
    public ResponseEntity<CommunityOrderHeader> createOrUpdateCommunityOrder(@RequestBody CommunityOrderRequest request) {
        CommunityOrderHeader updatedOrder = communityOrderService.createOrUpdateOrder(request);
        return ResponseEntity.ok(updatedOrder);
    }

    @GetMapping("/{communityId}")
    public ResponseEntity<CommunityOrderHeader> getCommunityOrder(
            @PathVariable String communityId,
            @RequestParam("type") CalendarUnit type) {
        CommunityOrderHeader header = aggregationService.getAggregatedCommunityOrder(communityId, type);
        return ResponseEntity.ok(header);
    }
}