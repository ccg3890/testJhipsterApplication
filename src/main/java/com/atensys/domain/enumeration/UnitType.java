package com.atensys.domain.enumeration;

/**
 * 단위
 */
public enum UnitType {
    COUNT("개수"),
    PERCENT("퍼센트");

    private final String value;

    UnitType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
