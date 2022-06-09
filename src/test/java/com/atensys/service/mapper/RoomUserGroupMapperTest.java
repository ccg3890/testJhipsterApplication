package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RoomUserGroupMapperTest {

    private RoomUserGroupMapper roomUserGroupMapper;

    @BeforeEach
    public void setUp() {
        roomUserGroupMapper = new RoomUserGroupMapperImpl();
    }
}
