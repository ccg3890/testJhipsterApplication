package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrgMapperTest {

    private OrgMapper orgMapper;

    @BeforeEach
    public void setUp() {
        orgMapper = new OrgMapperImpl();
    }
}
