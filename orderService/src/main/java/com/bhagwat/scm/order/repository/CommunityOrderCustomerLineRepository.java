package com.bhagwat.scm.order.repository;

import com.bhagwat.scm.order.entity.CommunityOrderCustomerLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityOrderCustomerLineRepository extends JpaRepository<CommunityOrderCustomerLine, Long> {
}
