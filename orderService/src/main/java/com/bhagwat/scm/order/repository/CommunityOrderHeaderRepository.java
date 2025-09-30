package com.bhagwat.scm.order.repository;

import com.bhagwat.scm.order.constant.CalendarUnit;
import com.bhagwat.scm.order.entity.CommunityOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommunityOrderHeaderRepository extends JpaRepository<CommunityOrderHeader, Long> {
    Optional<CommunityOrderHeader> findByCommunityIdAndSubscriptionCartType(String communityId, CalendarUnit type);
}

