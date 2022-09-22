package com.akipgenerationweb.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class UpdateGenerationProcessStatusAndPercentageExecutedMapperTest {

    private UpdateGenerationProcessStatusAndPercentageExecutedMapper updateGenerationProcessStatusAndPercentageExecutedMapper;

    @BeforeEach
    public void setUp() {
        updateGenerationProcessStatusAndPercentageExecutedMapper = new UpdateGenerationProcessStatusAndPercentageExecutedMapperImpl();
    }
}
