package com.atensys.domain.enumeration;

/**
 * 반복주기유형
 */
public enum FrequenceType {
    DAILY("매일"),
    WEEKLY("매주"),
    MONTHLY("매월"),
    YEARLY("매년"),
    WEEKDAY("주중 매일");

    private final String value;

    FrequenceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
