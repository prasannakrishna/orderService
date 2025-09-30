package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityOrderConsumer {

    private final MongoTemplate mongoTemplate;

    @KafkaListener(topics = "community-orders", groupId = "order-service")
    public void consumeCommunityOrder(CommunityOrderHeaderDocument doc) {
        mongoTemplate.save(doc, "community_orders");
        log.info("✅ Saved community order for communityId={} into Mongo", doc.getCommunityId());
    }
}