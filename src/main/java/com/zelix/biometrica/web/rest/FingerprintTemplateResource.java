package com.zelix.biometrica.web.rest;

import com.zelix.biometrica.repository.FingerprintTemplateRepository;
import com.zelix.biometrica.service.FingerprintTemplateService;
import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import com.zelix.biometrica.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.zelix.biometrica.domain.FingerprintTemplate}.
 */
@RestController
@RequestMapping("/api/fingerprint-templates")
public class FingerprintTemplateResource {

    private final Logger log = LoggerFactory.getLogger(FingerprintTemplateResource.class);

    private static final String ENTITY_NAME = "fingerprintTemplate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FingerprintTemplateService fingerprintTemplateService;

    private final FingerprintTemplateRepository fingerprintTemplateRepository;

    public FingerprintTemplateResource(
        FingerprintTemplateService fingerprintTemplateService,
        FingerprintTemplateRepository fingerprintTemplateRepository
    ) {
        this.fingerprintTemplateService = fingerprintTemplateService;
        this.fingerprintTemplateRepository = fingerprintTemplateRepository;
    }

    /**
     * {@code POST  /fingerprint-templates} : Create a new fingerprintTemplate.
     *
     * @param fingerprintTemplateDTO the fingerprintTemplateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fingerprintTemplateDTO, or with status {@code 400 (Bad Request)} if the fingerprintTemplate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FingerprintTemplateDTO> createFingerprintTemplate(@RequestBody FingerprintTemplateDTO fingerprintTemplateDTO)
        throws URISyntaxException {
        log.debug("REST request to save FingerprintTemplate : {}", fingerprintTemplateDTO);
        if (fingerprintTemplateDTO.getId() != null) {
            throw new BadRequestAlertException("A new fingerprintTemplate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FingerprintTemplateDTO result = fingerprintTemplateService.save(fingerprintTemplateDTO);
        return ResponseEntity
            .created(new URI("/api/fingerprint-templates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fingerprint-templates/:id} : Updates an existing fingerprintTemplate.
     *
     * @param id the id of the fingerprintTemplateDTO to save.
     * @param fingerprintTemplateDTO the fingerprintTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fingerprintTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the fingerprintTemplateDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fingerprintTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FingerprintTemplateDTO> updateFingerprintTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FingerprintTemplateDTO fingerprintTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to update FingerprintTemplate : {}, {}", id, fingerprintTemplateDTO);
        if (fingerprintTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fingerprintTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fingerprintTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        FingerprintTemplateDTO result = fingerprintTemplateService.update(fingerprintTemplateDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fingerprintTemplateDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /fingerprint-templates/:id} : Partial updates given fields of an existing fingerprintTemplate, field will ignore if it is null
     *
     * @param id the id of the fingerprintTemplateDTO to save.
     * @param fingerprintTemplateDTO the fingerprintTemplateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fingerprintTemplateDTO,
     * or with status {@code 400 (Bad Request)} if the fingerprintTemplateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fingerprintTemplateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fingerprintTemplateDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FingerprintTemplateDTO> partialUpdateFingerprintTemplate(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FingerprintTemplateDTO fingerprintTemplateDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update FingerprintTemplate partially : {}, {}", id, fingerprintTemplateDTO);
        if (fingerprintTemplateDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fingerprintTemplateDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fingerprintTemplateRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FingerprintTemplateDTO> result = fingerprintTemplateService.partialUpdate(fingerprintTemplateDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fingerprintTemplateDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /fingerprint-templates} : get all the fingerprintTemplates.
     *
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fingerprintTemplates in body.
     */
    @GetMapping("")
    public List<FingerprintTemplateDTO> getAllFingerprintTemplates(@RequestParam(name = "filter", required = false) String filter) {
        if ("fingerprint-is-null".equals(filter)) {
            log.debug("REST request to get all FingerprintTemplates where fingerprint is null");
            return fingerprintTemplateService.findAllWhereFingerprintIsNull();
        }
        log.debug("REST request to get all FingerprintTemplates");
        return fingerprintTemplateService.findAll();
    }

    /**
     * {@code GET  /fingerprint-templates/:id} : get the "id" fingerprintTemplate.
     *
     * @param id the id of the fingerprintTemplateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fingerprintTemplateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FingerprintTemplateDTO> getFingerprintTemplate(@PathVariable("id") Long id) {
        log.debug("REST request to get FingerprintTemplate : {}", id);
        Optional<FingerprintTemplateDTO> fingerprintTemplateDTO = fingerprintTemplateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fingerprintTemplateDTO);
    }

    /**
     * {@code DELETE  /fingerprint-templates/:id} : delete the "id" fingerprintTemplate.
     *
     * @param id the id of the fingerprintTemplateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFingerprintTemplate(@PathVariable("id") Long id) {
        log.debug("REST request to delete FingerprintTemplate : {}", id);
        fingerprintTemplateService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
