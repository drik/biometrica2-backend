package com.zelix.biometrica.service;

import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.zelix.biometrica.domain.FingerprintTemplate}.
 */
public interface FingerprintTemplateService {
    /**
     * Save a fingerprintTemplate.
     *
     * @param fingerprintTemplateDTO the entity to save.
     * @return the persisted entity.
     */
    FingerprintTemplateDTO save(FingerprintTemplateDTO fingerprintTemplateDTO);

    /**
     * Updates a fingerprintTemplate.
     *
     * @param fingerprintTemplateDTO the entity to update.
     * @return the persisted entity.
     */
    FingerprintTemplateDTO update(FingerprintTemplateDTO fingerprintTemplateDTO);

    /**
     * Partially updates a fingerprintTemplate.
     *
     * @param fingerprintTemplateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FingerprintTemplateDTO> partialUpdate(FingerprintTemplateDTO fingerprintTemplateDTO);

    /**
     * Get all the fingerprintTemplates.
     *
     * @return the list of entities.
     */
    List<FingerprintTemplateDTO> findAll();

    /**
     * Get all the FingerprintTemplateDTO where Fingerprint is {@code null}.
     *
     * @return the {@link List} of entities.
     */
    List<FingerprintTemplateDTO> findAllWhereFingerprintIsNull();

    /**
     * Get the "id" fingerprintTemplate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FingerprintTemplateDTO> findOne(Long id);

    /**
     * Delete the "id" fingerprintTemplate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
