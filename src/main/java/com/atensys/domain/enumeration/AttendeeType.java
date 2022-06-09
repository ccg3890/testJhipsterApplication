package com.atensys.domain.enumeration;

/**
 * 참석자유형
 */
public enum AttendeeType {
    INNER("참석자"),
    OUTTER("외부참석자");

    private final String value;

    AttendeeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
