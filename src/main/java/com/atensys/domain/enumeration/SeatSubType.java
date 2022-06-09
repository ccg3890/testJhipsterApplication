package com.atensys.domain.enumeration;

/**
 * 좌석부가유형
 */
public enum SeatSubType {
    NORMAL("일반석"),
    COLABORATION("협업석"),
    FOCUS("집중석"),
    STANDING("높낮이석");

    private final String value;

    SeatSubType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
