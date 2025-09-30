package com.bhagwat.scm.order.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class AllocationAuditFields {

    @Column(name = "order_quantity")
    private Integer orderQuantity;       // use for customer line order qty; product line uses totalOrderQuantity

    @Column(name = "shipped_quantity")
    private Integer shippedQuantity;

    @Column(name = "delivered_quantity")
    private Integer deliveredQuantity;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;

    @UpdateTimestamp
    @Column(name = "last_updated_date")
    private LocalDateTime lastUpdatedDate;

    @Column(name = "last_allocation_run_date")
    private LocalDateTime lastAllocationRunDate;

    @Column(name = "allocation_run_id")
    private String allocationRunId;

    @Column(name = "allocation_status")
    private String allocationStatus;

    @Column(name = "allocated_store_id")
    private String allocatedStoreId;

    @Column(name = "allocated_site")
    private String allocatedSite;

    @Column(name = "allocated_seller_division_id")
    private String allocatedSellerDivisionId;
}