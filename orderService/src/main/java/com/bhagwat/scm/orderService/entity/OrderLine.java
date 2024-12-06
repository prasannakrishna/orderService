package com.bhagwat.scm.orderService.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "orderline")
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

    // Getters and Setters
}
