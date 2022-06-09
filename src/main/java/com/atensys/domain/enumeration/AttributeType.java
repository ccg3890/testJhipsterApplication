package com.atensys.domain.enumeration;

/**
 * 속성종류
 */
public enum AttributeType {
    GLOBAL("전역"),
    COMPANY("회사"),
    OFFICE("오피스"),
    FLOOR("층"),
    ROOM("회의실"),
    SEAT("좌석"),
    LOCKER("락커");

    private final String value;

    AttributeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
