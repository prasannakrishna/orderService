package com.bhagwat.scm.orderService.command.repository;

import com.bhagwat.scm.orderService.command.entity.CommunityOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CommunityOrderRepository extends JpaRepository<CommunityOrder, UUID> {
    List<CommunityOrder> findByCommunityIdOrderByCreatedAtDesc(UUID communityId);
    Optional<CommunityOrder> findByCommunityOrderId(UUID communityOrderId);
}
