package com.bhagwat.scm.order.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
// FIX: Quote the schema and define the table name for best PostgreSQL compatibility
@Table(name = "\"order_line\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Long lineId;
    private String skuId;
    private int orderedQuantity;
    private int allocatedQuantity;
    private int shippedQuantity;
    private int deliveredQuantity;

    // Lombok handles Getters and Setters via @Data
}
