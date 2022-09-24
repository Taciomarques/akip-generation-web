package com.akipgenerationweb.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipAkipProcessDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipProcessDTO.class);
        AkipProcessDTO akipProcessDTO1 = new AkipProcessDTO();
        akipProcessDTO1.setId(1L);
        AkipProcessDTO akipProcessDTO2 = new AkipProcessDTO();
        assertThat(akipProcessDTO1).isNotEqualTo(akipProcessDTO2);
        akipProcessDTO2.setId(akipProcessDTO1.getId());
        assertThat(akipProcessDTO1).isEqualTo(akipProcessDTO2);
        akipProcessDTO2.setId(2L);
        assertThat(akipProcessDTO1).isNotEqualTo(akipProcessDTO2);
        akipProcessDTO1.setId(null);
        assertThat(akipProcessDTO1).isNotEqualTo(akipProcessDTO2);
    }
}
