package com.zelix.biometrica.service.impl;

import com.zelix.biometrica.domain.FingerprintTemplate;
import com.zelix.biometrica.repository.FingerprintTemplateRepository;
import com.zelix.biometrica.service.FingerprintTemplateService;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.zelix.biometrica.domain.FingerprintTemplate}.
 */
@Service
@Transactional
public class FingerprintTemplateServiceImpl implements FingerprintTemplateService {

    private final Logger log = LoggerFactory.getLogger(FingerprintTemplateServiceImpl.class);

    private final FingerprintTemplateRepository fingerprintTemplateRepository;

    public FingerprintTemplateServiceImpl(FingerprintTemplateRepository fingerprintTemplateRepository) {
        this.fingerprintTemplateRepository = fingerprintTemplateRepository;
    }

    @Override
    public FingerprintTemplate save(FingerprintTemplate fingerprintTemplate) {
        log.debug("Request to save FingerprintTemplate : {}", fingerprintTemplate);
        return fingerprintTemplateRepository.save(fingerprintTemplate);
    }

    @Override
    public FingerprintTemplate update(FingerprintTemplate fingerprintTemplate) {
        log.debug("Request to update FingerprintTemplate : {}", fingerprintTemplate);
        return fingerprintTemplateRepository.save(fingerprintTemplate);
    }

    @Override
    public Optional<FingerprintTemplate> partialUpdate(FingerprintTemplate fingerprintTemplate) {
        log.debug("Request to partially update FingerprintTemplate : {}", fingerprintTemplate);

        return fingerprintTemplateRepository
            .findById(fingerprintTemplate.getId())
            .map(existingFingerprintTemplate -> {
                if (fingerprintTemplate.getTemplateVersion() != null) {
                    existingFingerprintTemplate.setTemplateVersion(fingerprintTemplate.getTemplateVersion());
                }
                if (fingerprintTemplate.getTemplateData() != null) {
                    existingFingerprintTemplate.setTemplateData(fingerprintTemplate.getTemplateData());
                }
                if (fingerprintTemplate.getTemplateDataContentType() != null) {
                    existingFingerprintTemplate.setTemplateDataContentType(fingerprintTemplate.getTemplateDataContentType());
                }
                if (fingerprintTemplate.getOriginalImage() != null) {
                    existingFingerprintTemplate.setOriginalImage(fingerprintTemplate.getOriginalImage());
                }
                if (fingerprintTemplate.getOriginalImageContentType() != null) {
                    existingFingerprintTemplate.setOriginalImageContentType(fingerprintTemplate.getOriginalImageContentType());
                }
                if (fingerprintTemplate.getOriginalImageMime() != null) {
                    existingFingerprintTemplate.setOriginalImageMime(fingerprintTemplate.getOriginalImageMime());
                }
                if (fingerprintTemplate.getOriginalImageExtension() != null) {
                    existingFingerprintTemplate.setOriginalImageExtension(fingerprintTemplate.getOriginalImageExtension());
                }

                return existingFingerprintTemplate;
            })
            .map(fingerprintTemplateRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FingerprintTemplate> findAll() {
        log.debug("Request to get all FingerprintTemplates");
        return fingerprintTemplateRepository.findAll();
    }

    /**
     *  Get all the fingerprintTemplates where Fingerprint is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FingerprintTemplate> findAllWhereFingerprintIsNull() {
        log.debug("Request to get all fingerprintTemplates where Fingerprint is null");
        return StreamSupport
            .stream(fingerprintTemplateRepository.findAll().spliterator(), false)
            .filter(fingerprintTemplate -> fingerprintTemplate.getFingerprint() == null)
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FingerprintTemplate> findOne(Long id) {
        log.debug("Request to get FingerprintTemplate : {}", id);
        return fingerprintTemplateRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FingerprintTemplate : {}", id);
        fingerprintTemplateRepository.deleteById(id);
    }
}
