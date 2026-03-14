package com.bhagwat.scm.orderService.kafka;

import com.bhagwat.scm.orderService.command.entity.CustomerOrder;
import com.bhagwat.scm.orderService.command.entity.CommunityOrder;
import com.bhagwat.scm.orderService.command.repository.CustomerOrderRepository;
import com.bhagwat.scm.orderService.command.repository.CommunityOrderRepository;
import com.bhagwat.scm.orderService.constant.OrderStatus;
import com.bhagwat.scm.orderService.constant.PaymentStatus;
import com.bhagwat.scm.orderService.kafka.event.CustomerOrderEvent;
import com.bhagwat.scm.orderService.kafka.event.CommunityOrderEvent;
import com.bhagwat.scm.orderService.query.entity.CustomerOrderDocument;
import com.bhagwat.scm.orderService.query.entity.CommunityOrderDocument;
import com.bhagwat.scm.orderService.query.repository.CustomerOrderMongoRepository;
import com.bhagwat.scm.orderService.query.repository.CommunityOrderMongoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@Slf4j
public class OrderEventConsumer {

    private final CustomerOrderRepository customerOrderRepository;
    private final CommunityOrderRepository communityOrderRepository;
    private final CustomerOrderMongoRepository customerOrderMongoRepository;
    private final CommunityOrderMongoRepository communityOrderMongoRepository;
    private final ObjectMapper objectMapper;

    public OrderEventConsumer(CustomerOrderRepository customerOrderRepository,
                               CommunityOrderRepository communityOrderRepository,
                               CustomerOrderMongoRepository customerOrderMongoRepository,
                               CommunityOrderMongoRepository communityOrderMongoRepository,
                               ObjectMapper objectMapper) {
        this.customerOrderRepository = customerOrderRepository;
        this.communityOrderRepository = communityOrderRepository;
        this.customerOrderMongoRepository = customerOrderMongoRepository;
        this.communityOrderMongoRepository = communityOrderMongoRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "customer-order-events", groupId = "order-service-group")
    @Transactional
    public void handleCustomerOrderEvent(CustomerOrderEvent event) {
        log.info("Received CustomerOrderEvent: {}", event.getEventType());
        switch (event.getEventType()) {
            case "CUSTOMER_ORDER_CREATED" -> {
                CustomerOrder order = new CustomerOrder();
                order.setOrderId(event.getOrderId());
                order.setCustomerId(event.getCustomerId());
                order.setProductId(event.getProductId());
                order.setVariantId(event.getVariantId());
                order.setQuantity(event.getQuantity());
                order.setPricePerUnit(event.getPricePerUnit());
                order.setTotalAmount(event.getTotalAmount());
                order.setShippingCost(event.getShippingCost());
                order.setTaxAmount(event.getTaxAmount());
                order.setCurrency(event.getCurrency() != null ? event.getCurrency() : "USD");
                order.setCommunityId(event.getCommunityId());
                order.setSellerId(event.getSellerId());
                order.setInventoryKey(event.getInventoryKey());
                order.setShippingAddressJson(event.getShippingAddressJson());
                order.setOrderStatus(OrderStatus.CONFIRMED);
                order.setPaymentStatus(PaymentStatus.PAID);
                order.setCreatedAt(event.getCreatedAt() != null ? event.getCreatedAt() : Instant.now());
                order.setUpdatedAt(Instant.now());
                CustomerOrder saved = customerOrderRepository.save(order);

                CustomerOrderDocument doc = new CustomerOrderDocument();
                doc.setId(saved.getId().toString());
                doc.setOrderId(saved.getOrderId().toString());
                doc.setCustomerId(saved.getCustomerId().toString());
                doc.setProductId(saved.getProductId().toString());
                if (saved.getVariantId() != null) doc.setVariantId(saved.getVariantId().toString());
                doc.setQuantity(saved.getQuantity());
                doc.setPricePerUnit(saved.getPricePerUnit());
                doc.setTotalAmount(saved.getTotalAmount());
                doc.setCurrency(saved.getCurrency());
                doc.setOrderStatus(saved.getOrderStatus().name());
                doc.setPaymentStatus(saved.getPaymentStatus().name());
                doc.setCreatedAt(saved.getCreatedAt());
                doc.setUpdatedAt(saved.getUpdatedAt());
                customerOrderMongoRepository.save(doc);
            }
            case "CUSTOMER_ORDER_CANCELLED" -> customerOrderRepository.findByOrderId(event.getOrderId())
                    .ifPresent(o -> {
                        o.setOrderStatus(OrderStatus.CANCELLED);
                        o.setUpdatedAt(Instant.now());
                        customerOrderRepository.save(o);
                        customerOrderMongoRepository.findByOrderId(o.getOrderId().toString())
                                .ifPresent(d -> { d.setOrderStatus(OrderStatus.CANCELLED.name()); customerOrderMongoRepository.save(d); });
                    });
            default -> log.warn("Unknown CustomerOrderEvent type: {}", event.getEventType());
        }
    }

    @KafkaListener(topics = "community-order-events", groupId = "order-service-group")
    @Transactional
    public void handleCommunityOrderEvent(CommunityOrderEvent event) {
        log.info("Received CommunityOrderEvent: {}", event.getEventType());
        if ("COMMUNITY_ORDER_CREATED".equals(event.getEventType())) {
            String itemsJson;
            try { itemsJson = objectMapper.writeValueAsString(event.getItems()); } catch (Exception e) { itemsJson = "[]"; }

            CommunityOrder order = new CommunityOrder();
            order.setCommunityOrderId(event.getCommunityOrderId() != null ? event.getCommunityOrderId() : UUID.randomUUID());
            order.setCommunityId(event.getCommunityId());
            order.setCalendarUnit(event.getCalendarUnit());
            order.setItemsJson(itemsJson);
            order.setTotalAmount(event.getTotalAmount());
            order.setCurrency(event.getCurrency() != null ? event.getCurrency() : "USD");
            order.setOrderStatus(OrderStatus.CONFIRMED);
            order.setCheckoutDate(event.getCheckoutDate());
            order.setCreatedAt(event.getCreatedAt() != null ? event.getCreatedAt() : Instant.now());
            order.setUpdatedAt(Instant.now());
            CommunityOrder saved = communityOrderRepository.save(order);

            CommunityOrderDocument doc = new CommunityOrderDocument();
            doc.setId(saved.getId().toString());
            doc.setCommunityOrderId(saved.getCommunityOrderId().toString());
            doc.setCommunityId(saved.getCommunityId().toString());
            doc.setCalendarUnit(saved.getCalendarUnit());
            doc.setItemsJson(saved.getItemsJson());
            doc.setTotalAmount(saved.getTotalAmount());
            doc.setCurrency(saved.getCurrency());
            doc.setOrderStatus(saved.getOrderStatus().name());
            doc.setCheckoutDate(saved.getCheckoutDate());
            doc.setCreatedAt(saved.getCreatedAt());
            communityOrderMongoRepository.save(doc);
        }
    }
}
