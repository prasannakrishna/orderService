package com.bhagwat.scm.orderService.query.service;

import com.bhagwat.scm.orderService.query.entity.CustomerOrderDocument;
import com.bhagwat.scm.orderService.query.entity.CommunityOrderDocument;
import com.bhagwat.scm.orderService.query.repository.CustomerOrderMongoRepository;
import com.bhagwat.scm.orderService.query.repository.CommunityOrderMongoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class OrderQueryService {
    private final CustomerOrderMongoRepository customerOrderMongoRepository;
    private final CommunityOrderMongoRepository communityOrderMongoRepository;

    public OrderQueryService(CustomerOrderMongoRepository customerOrderMongoRepository,
                              CommunityOrderMongoRepository communityOrderMongoRepository) {
        this.customerOrderMongoRepository = customerOrderMongoRepository;
        this.communityOrderMongoRepository = communityOrderMongoRepository;
    }

    public List<CustomerOrderDocument> getCustomerOrders(UUID customerId) {
        return customerOrderMongoRepository.findByCustomerIdOrderByCreatedAtDesc(customerId.toString());
    }

    public CustomerOrderDocument getCustomerOrderById(UUID orderId) {
        return customerOrderMongoRepository.findByOrderId(orderId.toString()).orElse(null);
    }

    public List<CommunityOrderDocument> getCommunityOrders(UUID communityId) {
        return communityOrderMongoRepository.findByCommunityIdOrderByCreatedAtDesc(communityId.toString());
    }

    public CommunityOrderDocument getCommunityOrderById(UUID communityOrderId) {
        return communityOrderMongoRepository.findByCommunityOrderId(communityOrderId.toString()).orElse(null);
    }
}
