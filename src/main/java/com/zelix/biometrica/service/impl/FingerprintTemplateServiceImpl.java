package com.zelix.biometrica.service.impl;

import com.zelix.biometrica.domain.FingerprintTemplate;
import com.zelix.biometrica.repository.FingerprintTemplateRepository;
import com.zelix.biometrica.service.FingerprintTemplateService;
import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import com.zelix.biometrica.service.mapper.FingerprintTemplateMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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

    private final FingerprintTemplateMapper fingerprintTemplateMapper;

    public FingerprintTemplateServiceImpl(
        FingerprintTemplateRepository fingerprintTemplateRepository,
        FingerprintTemplateMapper fingerprintTemplateMapper
    ) {
        this.fingerprintTemplateRepository = fingerprintTemplateRepository;
        this.fingerprintTemplateMapper = fingerprintTemplateMapper;
    }

    @Override
    public FingerprintTemplateDTO save(FingerprintTemplateDTO fingerprintTemplateDTO) {
        log.debug("Request to save FingerprintTemplate : {}", fingerprintTemplateDTO);
        FingerprintTemplate fingerprintTemplate = fingerprintTemplateMapper.toEntity(fingerprintTemplateDTO);
        fingerprintTemplate = fingerprintTemplateRepository.save(fingerprintTemplate);
        return fingerprintTemplateMapper.toDto(fingerprintTemplate);
    }

    @Override
    public FingerprintTemplateDTO update(FingerprintTemplateDTO fingerprintTemplateDTO) {
        log.debug("Request to update FingerprintTemplate : {}", fingerprintTemplateDTO);
        FingerprintTemplate fingerprintTemplate = fingerprintTemplateMapper.toEntity(fingerprintTemplateDTO);
        fingerprintTemplate = fingerprintTemplateRepository.save(fingerprintTemplate);
        return fingerprintTemplateMapper.toDto(fingerprintTemplate);
    }

    @Override
    public Optional<FingerprintTemplateDTO> partialUpdate(FingerprintTemplateDTO fingerprintTemplateDTO) {
        log.debug("Request to partially update FingerprintTemplate : {}", fingerprintTemplateDTO);

        return fingerprintTemplateRepository
            .findById(fingerprintTemplateDTO.getId())
            .map(existingFingerprintTemplate -> {
                fingerprintTemplateMapper.partialUpdate(existingFingerprintTemplate, fingerprintTemplateDTO);

                return existingFingerprintTemplate;
            })
            .map(fingerprintTemplateRepository::save)
            .map(fingerprintTemplateMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FingerprintTemplateDTO> findAll() {
        log.debug("Request to get all FingerprintTemplates");
        return fingerprintTemplateRepository
            .findAll()
            .stream()
            .map(fingerprintTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     *  Get all the fingerprintTemplates where Fingerprint is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<FingerprintTemplateDTO> findAllWhereFingerprintIsNull() {
        log.debug("Request to get all fingerprintTemplates where Fingerprint is null");
        return StreamSupport
            .stream(fingerprintTemplateRepository.findAll().spliterator(), false)
            .filter(fingerprintTemplate -> fingerprintTemplate.getFingerprint() == null)
            .map(fingerprintTemplateMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FingerprintTemplateDTO> findOne(Long id) {
        log.debug("Request to get FingerprintTemplate : {}", id);
        return fingerprintTemplateRepository.findById(id).map(fingerprintTemplateMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete FingerprintTemplate : {}", id);
        fingerprintTemplateRepository.deleteById(id);
    }
}
