package com.bhagwat.scm.order.constant;

public enum CalendarUnit {
    DAY("D", "Day"),
    WEEK("W", "Week"),
    MONTH("M", "Month"),
    QUARTER("Q", "Quarter"),
    BIWEEKLY("B", "BiWeekly"),
    YEAR("Y", "Year");

    private final String code;
    private final String label;

    CalendarUnit(String code, String label) {
        this.code = code;
        this.label = label;
    }

    public String getCode() { return code; }
    public String getLabel() { return label; }
}