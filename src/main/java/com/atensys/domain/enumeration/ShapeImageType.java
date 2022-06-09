package com.atensys.domain.enumeration;

/**
 * 도형이미지구분
 */
public enum ShapeImageType {
    VECTOR("벡터"),
    IMAGE("이미지");

    private final String value;

    ShapeImageType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
