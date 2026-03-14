package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.common.OrderStatus;
import com.bhagwat.scm.order.common.OrderType;
import com.bhagwat.scm.order.constant.CalendarUnit;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import com.bhagwat.scm.order.entity.CommunityOrderProductLine;
import com.bhagwat.scm.order.event.CommunityOrderCreatedEvent;
import com.bhagwat.scm.order.repository.CommunityOrderHeaderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityOrderConsumer {

    private final MongoTemplate mongoTemplate;
    private final CommunityOrderHeaderRepository communityOrderHeaderRepository;

    /**
     * Consumes CommunityOrderCreatedEvent from cartService checkout.
     * Writes to both PostgreSQL (CommunityOrderHeader) and MongoDB (CommunityOrderHeaderDocument).
     */
    @KafkaListener(topics = "community-order-events", groupId = "order-service",
            containerFactory = "kafkaListenerContainerFactory")
    @Transactional
    public void consumeCommunityOrder(@Payload CommunityOrderCreatedEvent event) {
        log.info("Received community-order-events for communityId={} checkoutDate={}",
                event.getCommunityId(), event.getCheckoutDate());

        CalendarUnit calendarUnit = parseCalendarUnit(event.getCalendarUnit());
        LocalDate checkoutDate = event.getCheckoutDate() != null
                ? LocalDate.parse(event.getCheckoutDate())
                : LocalDate.now();

        // Build CommunityOrderHeader (PostgreSQL write model)
        CommunityOrderHeader header = CommunityOrderHeader.builder()
                .communityId(event.getCommunityId())
                .communityType(CommunityOrderHeader.CommunityType.PUBLIC)
                .orderStatus(OrderStatus.CREATED)
                .orderType(OrderType.CommunityOrder)
                .subscriptionCartType(calendarUnit)
                .cartCheckoutDate(checkoutDate)
                .orderValue(event.getTotalAmount() != null ? event.getTotalAmount().doubleValue() : 0.0)
                .orderCurrency(event.getCurrency() != null ? event.getCurrency() : "USD")
                .membersCount(0)
                .build();

        // Map product lines
        if (event.getItems() != null) {
            event.getItems().forEach(item -> {
                CommunityOrderProductLine productLine = CommunityOrderProductLine.builder()
                        .header(header)
                        .sellerId(item.getSellerId() != null ? item.getSellerId() : "unknown")
                        .productId(item.getProductId() != null ? item.getProductId() : "unknown")
                        .variantId(item.getVariantId() != null ? item.getVariantId() : "unknown")
                        .totalOrderQuantity(item.getQuantity() != null ? item.getQuantity() : 0)
                        .creationDate(LocalDateTime.now())
                        .lastUpdatedDate(LocalDateTime.now())
                        .build();
                header.getProductLines().add(productLine);
            });
        }

        communityOrderHeaderRepository.save(header);
        log.info("Saved CommunityOrderHeader to PostgreSQL for communityId={}", event.getCommunityId());

        // Build MongoDB document (read model)
        List<CommunityOrderHeaderDocument.ProductDemand> productDemands = event.getItems() != null
                ? event.getItems().stream()
                    .map(item -> CommunityOrderHeaderDocument.ProductDemand.builder()
                            .productId(item.getProductId())
                            .variantId(item.getVariantId())
                            .totalQuantity(item.getQuantity() != null ? item.getQuantity() : 0)
                            .build())
                    .collect(Collectors.toList())
                : List.of();

        CommunityOrderHeaderDocument doc = CommunityOrderHeaderDocument.builder()
                .id(event.getCommunityOrderId())
                .communityId(event.getCommunityId())
                .productDemand(productDemands)
                .build();

        mongoTemplate.save(doc, "community_orders");
        log.info("Saved CommunityOrderHeaderDocument to MongoDB for communityId={}", event.getCommunityId());
    }

    private CalendarUnit parseCalendarUnit(String value) {
        if (value == null) return CalendarUnit.DAY;
        try {
            return CalendarUnit.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CalendarUnit.DAY;
        }
    }
}
