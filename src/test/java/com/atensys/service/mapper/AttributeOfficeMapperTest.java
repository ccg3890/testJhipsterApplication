package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeOfficeMapperTest {

    private AttributeOfficeMapper attributeOfficeMapper;

    @BeforeEach
    public void setUp() {
        attributeOfficeMapper = new AttributeOfficeMapperImpl();
    }
}
