package com.bhagwat.scm.order.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "community_order_product_lines",
        indexes = {@Index(name = "idx_header_product_variant", columnList = "community_order_header_id, product_id, variant_id")})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CommunityOrderProductLine extends AllocationAuditFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_line_id")
    private Long productLineId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "community_order_header_id", nullable = false)
    private CommunityOrderHeader header;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "variant_id", nullable = false)
    private String variantId;

    // Aggregated quantity for the product/variant at community level
    @Column(name = "total_order_quantity")
    private Integer totalOrderQuantity;

    @OneToMany(mappedBy = "productLine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommunityOrderCustomerLine> customerLines = new ArrayList<>();
}