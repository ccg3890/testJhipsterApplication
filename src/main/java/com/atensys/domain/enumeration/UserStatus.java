package com.atensys.domain.enumeration;

/**
 * 사용자상태
 */
public enum UserStatus {
    TENURE("재직"),
    RETIREMENT("퇴직"),
    LEAVE("휴직"),
    EXTERNAL("외부용역직");

    private final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
