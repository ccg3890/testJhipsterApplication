package com.atensys.domain.enumeration;

/**
 * 예약반복종료유형
 */
public enum EndConditionType {
    NONE("없음"),
    DATE("날짜"),
    STEP("횟수");

    private final String value;

    EndConditionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
