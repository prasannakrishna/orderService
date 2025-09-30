package com.bhagwat.scm.order.common;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public enum OrderStatus {
    CREATED,
    RELEASED,
    ALLOCATED,
    PICKED,
    SHIPPED,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    READY_TO_DELIVER,
    READY_FOR_DELIVERY,
    DELIVERED,
    CANCELLED,
    HOLD,
    WAITING,
    CHECKED_OUT,
    VEHICLE_LOADED,
    ERROR;

    private static final Map<OrderType, Set<OrderStatus>> VALID_STATUSES = new EnumMap<>(OrderType.class);

    static {
        VALID_STATUSES.put(OrderType.CustomerOrder,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, PICKED, SHIPPED,
                        OUT_FOR_DELIVERY, DELIVERED, CANCELLED));

        VALID_STATUSES.put(OrderType.OrderToStore,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, SHIPPED, IN_TRANSIT,
                        READY_TO_DELIVER, DELIVERED, CANCELLED, HOLD));

        VALID_STATUSES.put(OrderType.OrderToStock,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, SHIPPED, IN_TRANSIT,
                        READY_FOR_DELIVERY, DELIVERED, HOLD, WAITING));

        VALID_STATUSES.put(OrderType.CommunityOrder,
                EnumSet.of(CREATED, RELEASED, CHECKED_OUT, ALLOCATED, PICKED,
                        VEHICLE_LOADED, IN_TRANSIT, READY_TO_DELIVER,
                        OUT_FOR_DELIVERY, DELIVERED, HOLD));

        VALID_STATUSES.put(OrderType.ReturnOrder,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, SHIPPED, IN_TRANSIT, DELIVERED, CANCELLED));

        VALID_STATUSES.put(OrderType.ReplenishmentOrder,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, SHIPPED, IN_TRANSIT, DELIVERED, HOLD));

        VALID_STATUSES.put(OrderType.ReadyToShipOrder,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, READY_TO_DELIVER, SHIPPED));

        VALID_STATUSES.put(OrderType.TransportPlanOrder,
                EnumSet.of(CREATED, RELEASED, ALLOCATED, IN_TRANSIT, DELIVERED, CANCELLED));
    }

    public static boolean isValidForType(OrderType type, OrderStatus status) {
        return VALID_STATUSES.getOrDefault(type, EnumSet.noneOf(OrderStatus.class))
                .contains(status);
    }

    public static Set<OrderStatus> validStatuses(OrderType type) {
        return VALID_STATUSES.getOrDefault(type, EnumSet.noneOf(OrderStatus.class));
    }
}
