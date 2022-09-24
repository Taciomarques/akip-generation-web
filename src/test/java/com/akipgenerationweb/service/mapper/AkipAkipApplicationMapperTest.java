package com.akipgenerationweb.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;

class AkipAkipApplicationMapperTest {

    private AkipApplicationMapper akipApplicationMapper;

    @BeforeEach
    public void setUp() {
        akipApplicationMapper = new ApplicationMapperImpl();
    }
}
