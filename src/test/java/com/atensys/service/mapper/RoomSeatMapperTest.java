package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomSeatMapperTest {

    private RoomSeatMapper roomSeatMapper;

    @BeforeEach
    public void setUp() {
        roomSeatMapper = new RoomSeatMapperImpl();
    }
}
