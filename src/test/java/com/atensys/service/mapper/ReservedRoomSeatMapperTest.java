package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservedRoomSeatMapperTest {

    private ReservedRoomSeatMapper reservedRoomSeatMapper;

    @BeforeEach
    public void setUp() {
        reservedRoomSeatMapper = new ReservedRoomSeatMapperImpl();
    }
}
