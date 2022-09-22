package com.akipgenerationweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UpdateGenerationProcessStatusAndPercentageExecutedTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpdateGenerationProcessStatusAndPercentageExecuted.class);
        UpdateGenerationProcessStatusAndPercentageExecuted updateGenerationProcessStatusAndPercentageExecuted1 = new UpdateGenerationProcessStatusAndPercentageExecuted();
        updateGenerationProcessStatusAndPercentageExecuted1.setId(1L);
        UpdateGenerationProcessStatusAndPercentageExecuted updateGenerationProcessStatusAndPercentageExecuted2 = new UpdateGenerationProcessStatusAndPercentageExecuted();
        updateGenerationProcessStatusAndPercentageExecuted2.setId(updateGenerationProcessStatusAndPercentageExecuted1.getId());
        assertThat(updateGenerationProcessStatusAndPercentageExecuted1).isEqualTo(updateGenerationProcessStatusAndPercentageExecuted2);
        updateGenerationProcessStatusAndPercentageExecuted2.setId(2L);
        assertThat(updateGenerationProcessStatusAndPercentageExecuted1).isNotEqualTo(updateGenerationProcessStatusAndPercentageExecuted2);
        updateGenerationProcessStatusAndPercentageExecuted1.setId(null);
        assertThat(updateGenerationProcessStatusAndPercentageExecuted1).isNotEqualTo(updateGenerationProcessStatusAndPercentageExecuted2);
    }
}
