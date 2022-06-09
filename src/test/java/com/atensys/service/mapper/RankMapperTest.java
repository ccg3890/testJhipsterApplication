package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RankMapperTest {

    private RankMapper rankMapper;

    @BeforeEach
    public void setUp() {
        rankMapper = new RankMapperImpl();
    }
}
