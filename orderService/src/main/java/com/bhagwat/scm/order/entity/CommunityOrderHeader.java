package com.bhagwat.scm.order.entity;

import com.bhagwat.scm.order.common.OrderStatus;
import com.bhagwat.scm.order.common.OrderType;
import com.bhagwat.scm.order.constant.CalendarUnit;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "community_order_headers", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"community_id", "subscription_cart_type"})
})
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommunityOrderHeader implements Serializable {

    public enum CommunityType {
        PRIVATE,
        PUBLIC
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_type", nullable = false)
    private OrderType orderType;

    @Column(name = "community_id", nullable = false)
    private String communityId;

    @Enumerated(EnumType.STRING)
    @Column(name = "subscription_cart_type", nullable = false)
    private CalendarUnit subscriptionCartType;

    @Column(name = "community_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private CommunityType communityType;

    @Column(name = "current_members_count", nullable = false)
    private int membersCount;

    @Column(name = "category")
    private String category;

    @Column(name = "cart_checkout_date", nullable = false)
    private LocalDate cartCheckoutDate;

    @Column(name = "ship_by_date")
    private LocalDate shipByDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "community_keys", columnDefinition = "jsonb")
    private Set<String> communityKeys = new HashSet<>();

    @Column(name = "city")
    private String city;

    @Column(name = "order_value", nullable = false)
    private double orderValue;

    @Column(name = "order_currency", nullable = false)
    private String orderCurrency;

    @OneToMany(mappedBy = "header", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @Builder.Default
    private List<CommunityOrderProductLine> productLines = new ArrayList<>();

}