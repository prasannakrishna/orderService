package com.bhagwat.scm.order.dto;

import com.bhagwat.scm.order.common.OrderStatus;
import com.bhagwat.scm.order.common.OrderType;
import com.bhagwat.scm.order.constant.CalendarUnit;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CommunityOrderRequest {
    private String communityId;
    private CommunityOrderHeader.CommunityType communityType;
    private String category;
    private OrderStatus orderStatus;
    private OrderType orderType;
    private CalendarUnit subscriptionCartType;
    private LocalDate cartCheckoutDate;
    private int currentMembersCount;
    private LocalDate shipByDate;
    private LocalDate deliveryDate;
    private double orderValue;
    private String orderCurrency;
    private String city;

    private List<ProductLineRequest> productLines;

    @Data
    public static class ProductLineRequest {
        private String sellerId;
        private String productId;
        private String variantId;
        private int orderQuantity;
        private List<CustomerLineRequest> customerLines;
    }

    @Data
    public static class CustomerLineRequest {
        private String customerId;
        private String addressId;
        private String productId;
        private String variantId;
        private int orderQuantity;
        private String clusterId;
    }
}