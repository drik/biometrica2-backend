package com.zelix.biometrica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.zelix.biometrica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FingerprintDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FingerprintDTO.class);
        FingerprintDTO fingerprintDTO1 = new FingerprintDTO();
        fingerprintDTO1.setId(1L);
        FingerprintDTO fingerprintDTO2 = new FingerprintDTO();
        assertThat(fingerprintDTO1).isNotEqualTo(fingerprintDTO2);
        fingerprintDTO2.setId(fingerprintDTO1.getId());
        assertThat(fingerprintDTO1).isEqualTo(fingerprintDTO2);
        fingerprintDTO2.setId(2L);
        assertThat(fingerprintDTO1).isNotEqualTo(fingerprintDTO2);
        fingerprintDTO1.setId(null);
        assertThat(fingerprintDTO1).isNotEqualTo(fingerprintDTO2);
    }
}
