package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeCompanyMapperTest {

    private AttributeCompanyMapper attributeCompanyMapper;

    @BeforeEach
    public void setUp() {
        attributeCompanyMapper = new AttributeCompanyMapperImpl();
    }
}
