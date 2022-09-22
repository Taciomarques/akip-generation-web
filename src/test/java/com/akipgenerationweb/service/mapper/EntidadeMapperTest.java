package com.akipgenerationweb.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EntidadeMapperTest {

    private EntidadeMapper entidadeMapper;

    @BeforeEach
    public void setUp() {
        entidadeMapper = new EntidadeMapperImpl();
    }
}
