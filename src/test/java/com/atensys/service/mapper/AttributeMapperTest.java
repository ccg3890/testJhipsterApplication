package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeMapperTest {

    private AttributeMapper attributeMapper;

    @BeforeEach
    public void setUp() {
        attributeMapper = new AttributeMapperImpl();
    }
}
