package com.atensys.domain.enumeration;

/**
 * 코드유형
 */
public enum CodeType {
    STRING("문자열형"),
    NUMBER("숫자형"),
    OBJECT("객체형"),
    ARRAY("배열형");

    private final String value;

    CodeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
