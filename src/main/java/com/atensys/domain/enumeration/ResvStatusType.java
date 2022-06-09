package com.atensys.domain.enumeration;

/**
 * 예약상태유형
 */
public enum ResvStatusType {
    RESERVED("예약"),
    COMFIRMED("예약확정"),
    CANCELLED("취소"),
    AUTO_CANCELED("자동취소"),
    ENTRANCE("입실"),
    LEAVING("퇴실");

    private final String value;

    ResvStatusType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
