package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RecurrenceMapperTest {

    private RecurrenceMapper recurrenceMapper;

    @BeforeEach
    public void setUp() {
        recurrenceMapper = new RecurrenceMapperImpl();
    }
}
