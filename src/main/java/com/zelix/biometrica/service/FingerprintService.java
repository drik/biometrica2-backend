package com.zelix.biometrica.service;

import com.zelix.biometrica.service.dto.FingerprintDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.zelix.biometrica.domain.Fingerprint}.
 */
public interface FingerprintService {
    /**
     * Save a fingerprint.
     *
     * @param fingerprintDTO the entity to save.
     * @return the persisted entity.
     */
    FingerprintDTO save(FingerprintDTO fingerprintDTO);

    /**
     * Updates a fingerprint.
     *
     * @param fingerprintDTO the entity to update.
     * @return the persisted entity.
     */
    FingerprintDTO update(FingerprintDTO fingerprintDTO);

    /**
     * Partially updates a fingerprint.
     *
     * @param fingerprintDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FingerprintDTO> partialUpdate(FingerprintDTO fingerprintDTO);

    /**
     * Get all the fingerprints.
     *
     * @return the list of entities.
     */
    List<FingerprintDTO> findAll();

    /**
     * Get the "id" fingerprint.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FingerprintDTO> findOne(Long id);

    /**
     * Delete the "id" fingerprint.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
