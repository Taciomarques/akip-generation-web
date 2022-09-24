package com.akipgenerationweb.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipEntityDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipEntityDTO.class);
        AkipEntityDTO akipEntityDTO1 = new AkipEntityDTO();
        akipEntityDTO1.setId(1L);
        AkipEntityDTO akipEntityDTO2 = new AkipEntityDTO();
        assertThat(akipEntityDTO1).isNotEqualTo(akipEntityDTO2);
        akipEntityDTO2.setId(akipEntityDTO1.getId());
        assertThat(akipEntityDTO1).isEqualTo(akipEntityDTO2);
        akipEntityDTO2.setId(2L);
        assertThat(akipEntityDTO1).isNotEqualTo(akipEntityDTO2);
        akipEntityDTO1.setId(null);
        assertThat(akipEntityDTO1).isNotEqualTo(akipEntityDTO2);
    }
}
