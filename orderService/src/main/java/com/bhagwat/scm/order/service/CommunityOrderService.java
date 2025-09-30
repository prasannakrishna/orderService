package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.common.OrderStatus;
import com.bhagwat.scm.order.common.OrderType;
import com.bhagwat.scm.order.dto.CommunityOrderRequest;
import com.bhagwat.scm.order.entity.CommunityOrderCustomerLine;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import com.bhagwat.scm.order.entity.CommunityOrderProductLine;
import com.bhagwat.scm.order.repository.CommunityOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommunityOrderService {

    private final CommunityOrderHeaderRepository headerRepository;
    private final CommunityOrderProducer communityOrderProducer;

    public CommunityOrderHeader createOrUpdateOrder(CommunityOrderRequest request) {
        // Build Header
        CommunityOrderHeader header = CommunityOrderHeader.builder()
                .communityId(request.getCommunityId())
                .communityType(request.getCommunityType())
                .category(request.getCategory())
                .orderStatus(request.getOrderStatus())
                .orderType(request.getOrderType())
                .cartCheckoutDate(request.getCartCheckoutDate())
                .shipByDate(request.getShipByDate())
                .deliveryDate(request.getDeliveryDate())
                .orderValue(request.getOrderValue())
                .membersCount(request.getCurrentMembersCount())
                .subscriptionCartType(request.getSubscriptionCartType())
                .orderCurrency(request.getOrderCurrency())
                .city(request.getCity())
                .build();

        // Product + Customer Lines
        request.getProductLines().forEach(p -> {
            CommunityOrderProductLine productLine = CommunityOrderProductLine.builder()
                    .header(header)
                    .sellerId(p.getSellerId())
                    .productId(p.getProductId())
                    .variantId(p.getVariantId())
                    .orderQuantity(p.getOrderQuantity())
                    .creationDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build();

            p.getCustomerLines().forEach(c -> {
                CommunityOrderCustomerLine customerLine = CommunityOrderCustomerLine.builder()
                        .header(header)
                        .customerId(c.getCustomerId())
                        .addressId(c.getAddressId())
                        .clusterId(c.getClusterId())
                        .productId(c.getProductId())
                        .variantId(c.getVariantId())
                        .orderQuantity(c.getOrderQuantity())
                        .creationDate(LocalDateTime.now())
                        .lastUpdatedDate(LocalDateTime.now())
                        .productLine(productLine)
                        .build();
                productLine.getCustomerLines().add(customerLine);
            });

            header.getProductLines().add(productLine);
        });

        CommunityOrderHeader saved = headerRepository.save(header);

        if (OrderStatus.isValidForType(OrderType.CommunityOrder, saved.getOrderStatus())
                && (saved.getOrderStatus().name().equals(OrderStatus.RELEASED.name()))) {
            communityOrderProducer.publishCommunityOrder(saved);
        }
        return saved;
    }

}