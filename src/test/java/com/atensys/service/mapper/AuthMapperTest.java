package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthMapperTest {

    private AuthMapper authMapper;

    @BeforeEach
    public void setUp() {
        authMapper = new AuthMapperImpl();
    }
}
