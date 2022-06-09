package com.atensys.domain.enumeration;

/**
 * 좌석유형
 */
public enum SeatType {
    FULL("종일제"),
    PART("시간제"),
    PERIOD("기간제");

    private final String value;

    SeatType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
