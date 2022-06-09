package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttendeeMapperTest {

    private AttendeeMapper attendeeMapper;

    @BeforeEach
    public void setUp() {
        attendeeMapper = new AttendeeMapperImpl();
    }
}
