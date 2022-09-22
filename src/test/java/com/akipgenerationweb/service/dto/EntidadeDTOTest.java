package com.akipgenerationweb.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntidadeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EntidadeDTO.class);
        EntidadeDTO entidadeDTO1 = new EntidadeDTO();
        entidadeDTO1.setId(1L);
        EntidadeDTO entidadeDTO2 = new EntidadeDTO();
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
        entidadeDTO2.setId(entidadeDTO1.getId());
        assertThat(entidadeDTO1).isEqualTo(entidadeDTO2);
        entidadeDTO2.setId(2L);
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
        entidadeDTO1.setId(null);
        assertThat(entidadeDTO1).isNotEqualTo(entidadeDTO2);
    }
}
