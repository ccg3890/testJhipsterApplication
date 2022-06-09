package com.atensys.domain.enumeration;

/**
 * 도형종류
 */
public enum ShapeType {
    RECTANGLE("사각형"),
    TRIANBLE("삼각형"),
    PENTAGON("오각형"),
    HEXAGON("육각형"),
    OCTAGON("팔각형"),
    ELLIPSE("타원"),
    STAR("별"),
    CUSTOM("사용자정의");

    private final String value;

    ShapeType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
