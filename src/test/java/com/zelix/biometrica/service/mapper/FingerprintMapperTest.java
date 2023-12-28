package com.zelix.biometrica.service.mapper;

import org.junit.jupiter.api.BeforeEach;

class FingerprintMapperTest {

    private FingerprintMapper fingerprintMapper;

    @BeforeEach
    public void setUp() {
        fingerprintMapper = new FingerprintMapperImpl();
    }
}
