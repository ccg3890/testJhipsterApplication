package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ShapeAssetMapperTest {

    private ShapeAssetMapper shapeAssetMapper;

    @BeforeEach
    public void setUp() {
        shapeAssetMapper = new ShapeAssetMapperImpl();
    }
}
