package com.atensys.domain.enumeration;

/**
 * 세로정렬방법
 */
public enum AlignVertical {
    TOP("맨위"),
    MIDDLE("중간"),
    BOTTOM("맨아래");

    private final String value;

    AlignVertical(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
