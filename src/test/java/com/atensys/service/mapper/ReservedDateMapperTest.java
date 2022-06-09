package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservedDateMapperTest {

    private ReservedDateMapper reservedDateMapper;

    @BeforeEach
    public void setUp() {
        reservedDateMapper = new ReservedDateMapperImpl();
    }
}
