package com.zelix.biometrica.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FingerprintTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Fingerprint getFingerprintSample1() {
        return new Fingerprint().id(1L).uuid(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"));
    }

    public static Fingerprint getFingerprintSample2() {
        return new Fingerprint().id(2L).uuid(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"));
    }

    public static Fingerprint getFingerprintRandomSampleGenerator() {
        return new Fingerprint().id(longCount.incrementAndGet()).uuid(UUID.randomUUID());
    }
}
