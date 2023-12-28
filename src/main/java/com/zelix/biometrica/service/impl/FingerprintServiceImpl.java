package com.zelix.biometrica.service.impl;

import com.zelix.biometrica.domain.Fingerprint;
import com.zelix.biometrica.repository.FingerprintRepository;
import com.zelix.biometrica.service.FingerprintService;
import com.zelix.biometrica.service.dto.FingerprintDTO;
import com.zelix.biometrica.service.mapper.FingerprintMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.zelix.biometrica.domain.Fingerprint}.
 */
@Service
@Transactional
public class FingerprintServiceImpl implements FingerprintService {

    private final Logger log = LoggerFactory.getLogger(FingerprintServiceImpl.class);

    private final FingerprintRepository fingerprintRepository;

    private final FingerprintMapper fingerprintMapper;

    public FingerprintServiceImpl(FingerprintRepository fingerprintRepository, FingerprintMapper fingerprintMapper) {
        this.fingerprintRepository = fingerprintRepository;
        this.fingerprintMapper = fingerprintMapper;
    }

    @Override
    public FingerprintDTO save(FingerprintDTO fingerprintDTO) {
        log.debug("Request to save Fingerprint : {}", fingerprintDTO);
        Fingerprint fingerprint = fingerprintMapper.toEntity(fingerprintDTO);
        fingerprint = fingerprintRepository.save(fingerprint);
        return fingerprintMapper.toDto(fingerprint);
    }

    @Override
    public FingerprintDTO update(FingerprintDTO fingerprintDTO) {
        log.debug("Request to update Fingerprint : {}", fingerprintDTO);
        Fingerprint fingerprint = fingerprintMapper.toEntity(fingerprintDTO);
        fingerprint = fingerprintRepository.save(fingerprint);
        return fingerprintMapper.toDto(fingerprint);
    }

    @Override
    public Optional<FingerprintDTO> partialUpdate(FingerprintDTO fingerprintDTO) {
        log.debug("Request to partially update Fingerprint : {}", fingerprintDTO);

        return fingerprintRepository
            .findById(fingerprintDTO.getId())
            .map(existingFingerprint -> {
                fingerprintMapper.partialUpdate(existingFingerprint, fingerprintDTO);

                return existingFingerprint;
            })
            .map(fingerprintRepository::save)
            .map(fingerprintMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FingerprintDTO> findAll() {
        log.debug("Request to get all Fingerprints");
        return fingerprintRepository.findAll().stream().map(fingerprintMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FingerprintDTO> findOne(Long id) {
        log.debug("Request to get Fingerprint : {}", id);
        return fingerprintRepository.findById(id).map(fingerprintMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Fingerprint : {}", id);
        fingerprintRepository.deleteById(id);
    }
}
