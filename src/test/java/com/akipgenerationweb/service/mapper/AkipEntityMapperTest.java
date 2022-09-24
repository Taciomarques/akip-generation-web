package com.akipgenerationweb.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class AkipEntityMapperTest {

    private AkipEntityMapper akipEntityMapper;

    @BeforeEach
    public void setUp() {
        akipEntityMapper = new AkipEntityMapperImpl();
    }
}
