package com.bhagwat.scm.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "community_order_customer_details")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityOrderCustomerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id", nullable = false)
    private CommunityOrderHeader header;

    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Column(name = "address_id", nullable = false)
    private String addressId;

}
