package com.akipgenerationweb.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class UpdateGenerationProcessStatusAndPercentageExecutedDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UpdateGenerationProcessStatusAndPercentageExecutedDTO.class);
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO1 = new UpdateGenerationProcessStatusAndPercentageExecutedDTO();
        updateGenerationProcessStatusAndPercentageExecutedDTO1.setId(1L);
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO2 = new UpdateGenerationProcessStatusAndPercentageExecutedDTO();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedDTO1)
            .isNotEqualTo(updateGenerationProcessStatusAndPercentageExecutedDTO2);
        updateGenerationProcessStatusAndPercentageExecutedDTO2.setId(updateGenerationProcessStatusAndPercentageExecutedDTO1.getId());
        assertThat(updateGenerationProcessStatusAndPercentageExecutedDTO1)
            .isEqualTo(updateGenerationProcessStatusAndPercentageExecutedDTO2);
        updateGenerationProcessStatusAndPercentageExecutedDTO2.setId(2L);
        assertThat(updateGenerationProcessStatusAndPercentageExecutedDTO1)
            .isNotEqualTo(updateGenerationProcessStatusAndPercentageExecutedDTO2);
        updateGenerationProcessStatusAndPercentageExecutedDTO1.setId(null);
        assertThat(updateGenerationProcessStatusAndPercentageExecutedDTO1)
            .isNotEqualTo(updateGenerationProcessStatusAndPercentageExecutedDTO2);
    }
}
