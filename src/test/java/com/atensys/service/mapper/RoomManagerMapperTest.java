package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomManagerMapperTest {

    private RoomManagerMapper roomManagerMapper;

    @BeforeEach
    public void setUp() {
        roomManagerMapper = new RoomManagerMapperImpl();
    }
}
