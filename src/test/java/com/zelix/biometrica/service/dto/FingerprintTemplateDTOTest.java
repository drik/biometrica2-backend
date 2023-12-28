package com.zelix.biometrica.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.zelix.biometrica.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FingerprintTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FingerprintTemplateDTO.class);
        FingerprintTemplateDTO fingerprintTemplateDTO1 = new FingerprintTemplateDTO();
        fingerprintTemplateDTO1.setId(1L);
        FingerprintTemplateDTO fingerprintTemplateDTO2 = new FingerprintTemplateDTO();
        assertThat(fingerprintTemplateDTO1).isNotEqualTo(fingerprintTemplateDTO2);
        fingerprintTemplateDTO2.setId(fingerprintTemplateDTO1.getId());
        assertThat(fingerprintTemplateDTO1).isEqualTo(fingerprintTemplateDTO2);
        fingerprintTemplateDTO2.setId(2L);
        assertThat(fingerprintTemplateDTO1).isNotEqualTo(fingerprintTemplateDTO2);
        fingerprintTemplateDTO1.setId(null);
        assertThat(fingerprintTemplateDTO1).isNotEqualTo(fingerprintTemplateDTO2);
    }
}
