package com.zelix.biometrica.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class FingerprintTemplateMapperTest {

    private FingerprintTemplateMapper fingerprintTemplateMapper;

    @BeforeEach
    public void setUp() {
        fingerprintTemplateMapper = new FingerprintTemplateMapperImpl();
    }
}
