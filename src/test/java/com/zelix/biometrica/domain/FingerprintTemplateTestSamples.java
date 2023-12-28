package com.zelix.biometrica.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FingerprintTemplateTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static FingerprintTemplate getFingerprintTemplateSample1() {
        return new FingerprintTemplate()
            .id(1L)
            .templateVersion("templateVersion1")
            .originalImageMime("originalImageMime1")
            .originalImageExtension("originalImageExtension1");
    }

    public static FingerprintTemplate getFingerprintTemplateSample2() {
        return new FingerprintTemplate()
            .id(2L)
            .templateVersion("templateVersion2")
            .originalImageMime("originalImageMime2")
            .originalImageExtension("originalImageExtension2");
    }

    public static FingerprintTemplate getFingerprintTemplateRandomSampleGenerator() {
        return new FingerprintTemplate()
            .id(longCount.incrementAndGet())
            .templateVersion(UUID.randomUUID().toString())
            .originalImageMime(UUID.randomUUID().toString())
            .originalImageExtension(UUID.randomUUID().toString());
    }
}
