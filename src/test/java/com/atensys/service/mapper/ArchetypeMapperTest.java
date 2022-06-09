package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArchetypeMapperTest {

    private ArchetypeMapper archetypeMapper;

    @BeforeEach
    public void setUp() {
        archetypeMapper = new ArchetypeMapperImpl();
    }
}
