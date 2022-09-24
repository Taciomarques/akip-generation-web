package com.akipgenerationweb.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipAkipApplicationDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipApplicationDTO.class);
        AkipApplicationDTO akipApplicationDTO1 = new AkipApplicationDTO();
        akipApplicationDTO1.setId(1L);
        AkipApplicationDTO akipApplicationDTO2 = new AkipApplicationDTO();
        assertThat(akipApplicationDTO1).isNotEqualTo(akipApplicationDTO2);
        akipApplicationDTO2.setId(akipApplicationDTO1.getId());
        assertThat(akipApplicationDTO1).isEqualTo(akipApplicationDTO2);
        akipApplicationDTO2.setId(2L);
        assertThat(akipApplicationDTO1).isNotEqualTo(akipApplicationDTO2);
        akipApplicationDTO1.setId(null);
        assertThat(akipApplicationDTO1).isNotEqualTo(akipApplicationDTO2);
    }
}
