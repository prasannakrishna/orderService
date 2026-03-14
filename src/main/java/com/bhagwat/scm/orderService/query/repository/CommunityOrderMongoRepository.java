package com.bhagwat.scm.orderService.query.repository;

import com.bhagwat.scm.orderService.query.entity.CommunityOrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.Optional;

public interface CommunityOrderMongoRepository extends MongoRepository<CommunityOrderDocument, String> {
    List<CommunityOrderDocument> findByCommunityIdOrderByCreatedAtDesc(String communityId);
    Optional<CommunityOrderDocument> findByCommunityOrderId(String communityOrderId);
}
