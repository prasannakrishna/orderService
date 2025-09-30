package com.bhagwat.scm.order.entity;

import com.bhagwat.scm.order.constant.TrackingLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "community_order_lines")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityOrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "header_id", nullable = false)
    private CommunityOrderHeader header;

    @Column(name = "line_id", nullable = false)
    private Integer lineId;

    @Column(name = "product_id", nullable = false)
    private String productId;

    @Column(name = "total_Order_quantity", nullable = false)
    private int totalOrderedQuantity;

    @Column(name = "allocated_quantity")
    private int allocatedQuantity;

    @Column(name = "shipped_quantity")
    private int shippedQuantity;

    @Column(name = "delivered_quantity", nullable = false)
    private int deliveredQuantity;

    @Column(name = "sku_id", nullable = false)
    private Long skuId;

    @Column(name = "seller_id", nullable = false)
    private String sellerId;

    @Column(name = "line_value")
    private double lineValue;

    @Column(name = "inventory_key")
    private String inventoryKey;

    @Column(name = "line_currency")
    private String lineCurrency;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "product_search_keys", columnDefinition = "jsonb")
    private Set<String> productSearchKeys = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "shipping_tracking_level")
    private TrackingLevel shippingTrackingLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "tracking_level")
    private TrackingLevel trackingLevel;

    @Column(name = "line_weight")
    private double lineWeight;

    @Column(name = "line_volume")
    private double lineVolume;

    @Column(name = "weight_uom")
    private String weightUOM;

    @Column(name = "volume_uom")
    private String volumeUOM;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "last_modified_at")
    private Timestamp lastModifiedAt;
}
