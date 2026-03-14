package com.bhagwat.scm.orderService.query.repository;

import com.bhagwat.scm.orderService.query.entity.CustomerOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CustomerOrderMongoRepository extends MongoRepository<CustomerOrderDocument, String> {
    List<CustomerOrderDocument> findByCustomerIdOrderByCreatedAtDesc(String customerId);
    Optional<CustomerOrderDocument> findByOrderId(String orderId);
}
