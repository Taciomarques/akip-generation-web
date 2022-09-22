package com.akipgenerationweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EntidadeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Entidade.class);
        Entidade entidade1 = new Entidade();
        entidade1.setId(1L);
        Entidade entidade2 = new Entidade();
        entidade2.setId(entidade1.getId());
        assertThat(entidade1).isEqualTo(entidade2);
        entidade2.setId(2L);
        assertThat(entidade1).isNotEqualTo(entidade2);
        entidade1.setId(null);
        assertThat(entidade1).isNotEqualTo(entidade2);
    }
}
