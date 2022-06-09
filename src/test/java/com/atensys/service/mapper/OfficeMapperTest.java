package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OfficeMapperTest {

    private OfficeMapper officeMapper;

    @BeforeEach
    public void setUp() {
        officeMapper = new OfficeMapperImpl();
    }
}
