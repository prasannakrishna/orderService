package com.bhagwat.scm.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "community_order_customer_lines",
        indexes = {@Index(name = "idx_customer_header_product", columnList = "community_order_header_id, customer_id, product_id, variant_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommunityOrderCustomerLine extends AllocationAuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_line_id")
    private Long customerLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_order_header_id", nullable = false)
    private CommunityOrderHeader header;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_line_id", nullable = false)
    private CommunityOrderProductLine productLine;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "address_id", nullable = false)
    private String addressId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    @Column(name = "cluster_id", nullable = false)
    private String clusterId;

    // orderQuantity (customer request) lives in AllocationAuditFields.orderQuantity
    // shippedQuantity/deliveredQuantity also come from AllocationAuditFields
}