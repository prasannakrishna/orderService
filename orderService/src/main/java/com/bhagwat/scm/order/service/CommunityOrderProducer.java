package com.bhagwat.scm.order.service;

import com.bhagwat.scm.order.entity.CommunityOrderCustomerLine;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import com.bhagwat.scm.order.entity.CommunityOrderLineDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommunityOrderProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    public void publishCommunityOrder(CommunityOrderHeader header) {
        CommunityOrderHeaderDocument doc = mapToDocument(header);
        kafkaTemplate.send("community-orders", header.getCommunityId(), doc);
    }

    private CommunityOrderHeaderDocument mapToDocument(CommunityOrderHeader header) {
        // 1. Aggregate product demand
        Map<String, CommunityOrderHeaderDocument.ProductDemand> demandMap = new HashMap<>();

        header.getProductLines().forEach(pl -> {
            String key = pl.getProductId() + "_" + pl.getVariantId();
            demandMap.computeIfAbsent(key, k -> CommunityOrderHeaderDocument.ProductDemand.builder()
                    .productId(pl.getProductId())
                    .variantId(pl.getVariantId())
                    .totalQuantity(0)
                    .build()
            ).setTotalQuantity(
                    demandMap.get(key).getTotalQuantity() + pl.getOrderQuantity()
            );
        });

        // 2. Build clusters with customers (group by clusterId inside each product line)
        List<CommunityOrderLineDocument> clusters = header.getProductLines().stream()
                .flatMap(pl -> {
                    // group customer lines by clusterId
                    Map<String, List<CommunityOrderCustomerLine>> byCluster =
                            pl.getCustomerLines().stream()
                                    .collect(Collectors.groupingBy(CommunityOrderCustomerLine::getClusterId));

                    return byCluster.entrySet().stream().map(entry -> {
                        String clusterId = entry.getKey();
                        List<CommunityOrderCustomerLine> customerLines = entry.getValue();

                        // total cluster quantity = sum of all customer orders in this cluster
                        int totalClusterQuantity = customerLines.stream()
                                .mapToInt(CommunityOrderCustomerLine::getOrderQuantity)
                                .sum();

                        return CommunityOrderLineDocument.builder()
                                .communityClusterId("CC-" + clusterId + "-" + pl.getProductLineId())
                                .clusterId(clusterId)
                                .productId(pl.getProductId())
                                .variantId(pl.getVariantId())
                                .totalClusterQuantity(totalClusterQuantity)
                                .customers(customerLines.stream()
                                        .map(cl -> CommunityOrderLineDocument.CustomerOrder.builder()
                                                .customerId(cl.getCustomerId())
                                                .productId(cl.getProductId())
                                                .variantId(cl.getVariantId())
                                                .orderQuantity(cl.getOrderQuantity())
                                                .build())
                                        .toList())
                                .build();
                    });
                })
                .toList();

        return CommunityOrderHeaderDocument.builder()
                .communityId(header.getCommunityId())
                .productDemand(new ArrayList<>(demandMap.values()))
                .communityClusters(clusters)
                .build();
    }
}