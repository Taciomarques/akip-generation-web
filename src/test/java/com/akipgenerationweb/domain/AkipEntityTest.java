package com.akipgenerationweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipEntityTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipEntity.class);
        AkipEntity akipEntity1 = new AkipEntity();
        akipEntity1.setId(1L);
        AkipEntity akipEntity2 = new AkipEntity();
        akipEntity2.setId(akipEntity1.getId());
        assertThat(akipEntity1).isEqualTo(akipEntity2);
        akipEntity2.setId(2L);
        assertThat(akipEntity1).isNotEqualTo(akipEntity2);
        akipEntity1.setId(null);
        assertThat(akipEntity1).isNotEqualTo(akipEntity2);
    }
}
