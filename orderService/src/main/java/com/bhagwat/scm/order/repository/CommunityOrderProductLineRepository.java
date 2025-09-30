package com.bhagwat.scm.order.repository;

import com.bhagwat.scm.order.entity.CommunityOrderProductLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommunityOrderProductLineRepository extends JpaRepository<CommunityOrderProductLine, Long> {
}


