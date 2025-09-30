package com.bhagwat.scm.order.entity;

import com.bhagwat.scm.order.common.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import com.bhagwat.scm.order.common.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
// FIX: Removed 'schema' attribute. Table name is quoted and pluralized
// to avoid conflict with the SQL keyword 'ORDER'.
@Table(name = "\"orders\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime orderCreatedDate;
    private LocalDateTime orderLastUpdatedDate;
    private LocalDateTime orderShipByDate;
    private LocalDateTime orderDeliverByDate;
    private Integer numberOfLines;

    @ManyToOne
    @JoinColumn(name = "source_address_id")
    private Address sourceAddress;

    @ManyToOne
    @JoinColumn(name = "target_address_id")
    private Address targetAddress;

    @ManyToOne
    @JoinColumn(name = "carrier_id")
    private Party carrier;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private Party seller;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderLine> orderLines;

    private String consignmentId;
    private String customerId;

    // Lombok handles Getters and Setters via @Data
}
