package com.atensys.domain.enumeration;

/**
 * 도형자산유형
 */
public enum ShapeAssetType {
    SEAT("좌석"),
    ROOM("회의실"),
    LOCKER("사물함");

    private final String value;

    ShapeAssetType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
