package com.atensys.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryMapperTest {

    private DictionaryMapper dictionaryMapper;

    @BeforeEach
    public void setUp() {
        dictionaryMapper = new DictionaryMapperImpl();
    }
}
