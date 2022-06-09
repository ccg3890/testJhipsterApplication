package com.atensys.domain.enumeration;

/**
 * 참석여부 응답상태
 */
public enum AttendeeResponseStatus {
    NEEDS_ACTION("응답없음"),
    DECLINED("초대거부"),
    TENTATIVE("임시수락"),
    ACCEPTED("수락");

    private final String value;

    AttendeeResponseStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
