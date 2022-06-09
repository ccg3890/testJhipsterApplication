package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AttributeRoomMapperTest {

    private AttributeRoomMapper attributeRoomMapper;

    @BeforeEach
    public void setUp() {
        attributeRoomMapper = new AttributeRoomMapperImpl();
    }
}
