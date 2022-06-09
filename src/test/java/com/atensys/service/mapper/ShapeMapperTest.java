package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShapeMapperTest {

    private ShapeMapper shapeMapper;

    @BeforeEach
    public void setUp() {
        shapeMapper = new ShapeMapperImpl();
    }
}
