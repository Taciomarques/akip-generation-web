package com.akipgenerationweb.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class AkipProcessMapperTest {

    private AkipProcessMapper akipProcessMapper;

    @BeforeEach
    public void setUp() {
        akipProcessMapper = new AkipProcessMapperImpl();
    }
}
