package com.bhagwat.scm.order.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.util.backoff.FixedBackOff;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RefreshScope
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    /**
     * Configures a custom ObjectMapper for consistent JSON serialization/deserialization.
     * This ensures proper handling of Java 8 date/time types (like Instant).
     *
     * @return Configured ObjectMapper.
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        // Register JavaTimeModule for proper serialization of Java 8 Date/Time API
        objectMapper.registerModule(new JavaTimeModule());
        // Disable writing dates as numeric timestamps
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        // Subtype registration is commented out, relying on __TypeId__ headers for polymorphism.
        return objectMapper;
    }

    @Bean
    @RefreshScope
    public ProducerFactory<String, Object> producerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

        // Tells the JsonSerializer to include the Java class name in a message header (__TypeId__)
        // This is crucial for the consumer to know which concrete type to deserialize the JSON into.
        config.put(JsonSerializer.ADD_TYPE_INFO_HEADERS, true);

        // Instantiate JsonSerializer with our custom ObjectMapper
        return new DefaultKafkaProducerFactory<>(config, new StringSerializer(), new JsonSerializer<>(objectMapper));
    }

    /**
     * This bean is the one your CommunityOrderService requires, which is now correctly defined.
     */
    @Bean
    public KafkaTemplate<String, Object> kafkaTemplate(ProducerFactory<String, Object> producerFactory) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, Object> consumerFactory(ObjectMapper objectMapper) {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, "cart-subscription-group");
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);

        // IMPORTANT: Trusts all packages for deserialization based on the __TypeId__ header.
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");

        // Map cartService's fully-qualified class names to our local event classes,
        // so cross-service deserialization works even though packages differ.
        config.put(JsonDeserializer.TYPE_MAPPINGS,
                "com.bhagwat.retail.cart.dto.CustomerOrderCreatedEvent:com.bhagwat.scm.order.event.CustomerOrderCreatedEvent," +
                "com.bhagwat.retail.cart.dto.CommunityOrderCreatedEvent:com.bhagwat.scm.order.event.CommunityOrderCreatedEvent");

        // Use the custom JsonDeserializer initialized with our ObjectMapper and 'Object' as the default type.
        JsonDeserializer<Object> jsonDeserializer = new JsonDeserializer<>(Object.class, objectMapper);

        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), jsonDeserializer);
    }

    /**
     * Retry + Dead Letter Topic (DLT) error handler.
     * On consumer exception: retry 3 times with 1s backoff, then publish to
     * {topic}.DLT (e.g. customer-order-events.DLT) for manual inspection/replay.
     * This prevents a poison pill message from blocking the partition forever.
     */
    @Bean
    public DefaultErrorHandler errorHandler(KafkaTemplate<String, Object> kafkaTemplate) {
        DeadLetterPublishingRecoverer recoverer = new DeadLetterPublishingRecoverer(kafkaTemplate);
        // 3 retries, 1 second apart, then send to DLT
        FixedBackOff backOff = new FixedBackOff(1000L, 3L);
        return new DefaultErrorHandler(recoverer, backOff);
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, Object> kafkaListenerContainerFactory(
            ConsumerFactory<String, Object> consumerFactory,
            DefaultErrorHandler errorHandler) {
        ConcurrentKafkaListenerContainerFactory<String, Object> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        factory.setCommonErrorHandler(errorHandler);
        return factory;
    }
}