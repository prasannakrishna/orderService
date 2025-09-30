package com.bhagwat.scm.order.constant;
public enum TrackingLevel {
    EACH(0),
    CASE(1),
    BOX(2),
    PALLET(3),
    PACK(4),
    INNER_PACK(5),
    OUTER_PACK(6),
    CRATE(7),
    CONTAINER(8),
    TRUCK(9),
    SHIPMENT(10);

    private final short code;

    TrackingLevel(int code) {
        this.code = (short) code;
    }

    public short getCode() {
        return code;
    }

    public static TrackingLevel fromCode(short code) {
        for (TrackingLevel level : values()) {
            if (level.code == code) {
                return level;
            }
        }
        throw new IllegalArgumentException("Unknown TrackingLevel code: " + code);
    }
}