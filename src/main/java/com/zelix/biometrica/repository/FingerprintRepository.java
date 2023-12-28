package com.zelix.biometrica.repository;

import com.zelix.biometrica.domain.Fingerprint;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Fingerprint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FingerprintRepository extends JpaRepository<Fingerprint, Long> {}
