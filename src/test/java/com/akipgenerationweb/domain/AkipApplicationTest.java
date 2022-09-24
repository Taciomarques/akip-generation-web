package com.akipgenerationweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipApplicationTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipApplication.class);
        AkipApplication akipApplication1 = new AkipApplication();
        akipApplication1.setId(1L);
        AkipApplication akipApplication2 = new AkipApplication();
        akipApplication2.setId(akipApplication1.getId());
        assertThat(akipApplication1).isEqualTo(akipApplication2);
        akipApplication2.setId(2L);
        assertThat(akipApplication1).isNotEqualTo(akipApplication2);
        akipApplication1.setId(null);
        assertThat(akipApplication1).isNotEqualTo(akipApplication2);
    }
}
