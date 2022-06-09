package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PenaltyMapperTest {

    private PenaltyMapper penaltyMapper;

    @BeforeEach
    public void setUp() {
        penaltyMapper = new PenaltyMapperImpl();
    }
}
