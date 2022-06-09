package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeFloorMapperTest {

    private AttributeFloorMapper attributeFloorMapper;

    @BeforeEach
    public void setUp() {
        attributeFloorMapper = new AttributeFloorMapperImpl();
    }
}
