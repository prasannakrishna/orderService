package com.bhagwat.scm.order.repository;

import com.bhagwat.scm.order.entity.CommunityOrderHeaderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityOrderRepository extends MongoRepository<CommunityOrderHeaderDocument, String> {
    CommunityOrderHeaderDocument findByCommunityId(String communityId);
}