package com.akipgenerationweb.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.akipgenerationweb.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AkipProcessTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AkipProcess.class);
        AkipProcess process1 = new AkipProcess();
        process1.setId(1L);
        AkipProcess process2 = new AkipProcess();
        process2.setId(process1.getId());
        assertThat(process1).isEqualTo(process2);
        process2.setId(2L);
        assertThat(process1).isNotEqualTo(process2);
        process1.setId(null);
        assertThat(process1).isNotEqualTo(process2);
    }
}
