package com.bhagwat.scm.order.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RefreshScope
public class KafkaTopicConfig {

    // --- Order Events Topic Properties ---
    // These values must be defined in your application.yml or configuration service
    @Value("${app.kafka.topics.order-events.name:order-events-topic}")
    private String orderEventsTopicName;

    @Value("${app.kafka.topics.order-events.partitions:3}")
    private int orderEventsTopicPartitions;

    @Value("${app.kafka.topics.order-events.replicas:1}")
    private short orderEventsTopicReplicas;

    @Value("${app.kafka.topics.order-events.retention-ms:604800000}") // Default: 7 days
    private String orderEventsTopicRetentionMs;

    @Value("${app.kafka.topics.order-events.cleanup-policy:delete}")
    private String orderEventsTopicCleanupPolicy;


    /**
     * Creates and configures the 'order-events' Kafka topic.
     * This topic is typically used for publishing events like "OrderCreated" or "OrderUpdated."
     *
     * @return NewTopic object representing the configured Kafka topic.
     */
    @Bean
    @RefreshScope
    public NewTopic orderEventsTopic() {
        Map<String, String> configs = new HashMap<>();
        // Apply custom retention and cleanup policy from properties
        configs.put("retention.ms", orderEventsTopicRetentionMs);
        configs.put("cleanup.policy", orderEventsTopicCleanupPolicy);

        return TopicBuilder.name(orderEventsTopicName)
                .partitions(orderEventsTopicPartitions)
                .replicas(orderEventsTopicReplicas)
                .configs(configs)
                .build();
    }
}
