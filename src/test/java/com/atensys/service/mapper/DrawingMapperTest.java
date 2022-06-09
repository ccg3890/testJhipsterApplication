package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DrawingMapperTest {

    private DrawingMapper drawingMapper;

    @BeforeEach
    public void setUp() {
        drawingMapper = new DrawingMapperImpl();
    }
}
