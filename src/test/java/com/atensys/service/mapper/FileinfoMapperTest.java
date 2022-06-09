package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FileinfoMapperTest {

    private FileinfoMapper fileinfoMapper;

    @BeforeEach
    public void setUp() {
        fileinfoMapper = new FileinfoMapperImpl();
    }
}
