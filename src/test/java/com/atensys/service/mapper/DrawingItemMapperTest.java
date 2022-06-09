package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrawingItemMapperTest {

    private DrawingItemMapper drawingItemMapper;

    @BeforeEach
    public void setUp() {
        drawingItemMapper = new DrawingItemMapperImpl();
    }
}
