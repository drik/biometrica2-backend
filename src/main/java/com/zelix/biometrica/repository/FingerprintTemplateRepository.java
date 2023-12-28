package com.zelix.biometrica.repository;

import com.zelix.biometrica.domain.FingerprintTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FingerprintTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FingerprintTemplateRepository extends JpaRepository<FingerprintTemplate, Long> {}
