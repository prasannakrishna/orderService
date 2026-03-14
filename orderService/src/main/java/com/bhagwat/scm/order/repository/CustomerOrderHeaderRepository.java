package com.bhagwat.scm.order.repository;

import com.bhagwat.scm.order.entity.CustomerOrderHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerOrderHeaderRepository extends JpaRepository<CustomerOrderHeader, String> {
    List<CustomerOrderHeader> findByCustomerId(String customerId);
}
