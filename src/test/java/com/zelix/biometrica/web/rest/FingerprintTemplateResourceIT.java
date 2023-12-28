package com.zelix.biometrica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zelix.biometrica.IntegrationTest;
import com.zelix.biometrica.domain.FingerprintTemplate;
import com.zelix.biometrica.repository.FingerprintTemplateRepository;
import com.zelix.biometrica.service.dto.FingerprintTemplateDTO;
import com.zelix.biometrica.service.mapper.FingerprintTemplateMapper;
import jakarta.persistence.EntityManager;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link FingerprintTemplateResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FingerprintTemplateResourceIT {

    private static final String DEFAULT_TEMPLATE_VERSION = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE_VERSION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_TEMPLATE_DATA = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_TEMPLATE_DATA = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_TEMPLATE_DATA_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_TEMPLATE_DATA_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_ORIGINAL_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ORIGINAL_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ORIGINAL_IMAGE_MIME = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_IMAGE_MIME = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_IMAGE_EXTENSION = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_IMAGE_EXTENSION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fingerprint-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FingerprintTemplateRepository fingerprintTemplateRepository;

    @Autowired
    private FingerprintTemplateMapper fingerprintTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFingerprintTemplateMockMvc;

    private FingerprintTemplate fingerprintTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FingerprintTemplate createEntity(EntityManager em) {
        FingerprintTemplate fingerprintTemplate = new FingerprintTemplate()
            .templateVersion(DEFAULT_TEMPLATE_VERSION)
            .templateData(DEFAULT_TEMPLATE_DATA)
            .templateDataContentType(DEFAULT_TEMPLATE_DATA_CONTENT_TYPE)
            .originalImage(DEFAULT_ORIGINAL_IMAGE)
            .originalImageContentType(DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE)
            .originalImageMime(DEFAULT_ORIGINAL_IMAGE_MIME)
            .originalImageExtension(DEFAULT_ORIGINAL_IMAGE_EXTENSION);
        return fingerprintTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FingerprintTemplate createUpdatedEntity(EntityManager em) {
        FingerprintTemplate fingerprintTemplate = new FingerprintTemplate()
            .templateVersion(UPDATED_TEMPLATE_VERSION)
            .templateData(UPDATED_TEMPLATE_DATA)
            .templateDataContentType(UPDATED_TEMPLATE_DATA_CONTENT_TYPE)
            .originalImage(UPDATED_ORIGINAL_IMAGE)
            .originalImageContentType(UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE)
            .originalImageMime(UPDATED_ORIGINAL_IMAGE_MIME)
            .originalImageExtension(UPDATED_ORIGINAL_IMAGE_EXTENSION);
        return fingerprintTemplate;
    }

    @BeforeEach
    public void initTest() {
        fingerprintTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createFingerprintTemplate() throws Exception {
        int databaseSizeBeforeCreate = fingerprintTemplateRepository.findAll().size();
        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);
        restFingerprintTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        FingerprintTemplate testFingerprintTemplate = fingerprintTemplateList.get(fingerprintTemplateList.size() - 1);
        assertThat(testFingerprintTemplate.getTemplateVersion()).isEqualTo(DEFAULT_TEMPLATE_VERSION);
        assertThat(testFingerprintTemplate.getTemplateData()).isEqualTo(DEFAULT_TEMPLATE_DATA);
        assertThat(testFingerprintTemplate.getTemplateDataContentType()).isEqualTo(DEFAULT_TEMPLATE_DATA_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImage()).isEqualTo(DEFAULT_ORIGINAL_IMAGE);
        assertThat(testFingerprintTemplate.getOriginalImageContentType()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImageMime()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_MIME);
        assertThat(testFingerprintTemplate.getOriginalImageExtension()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_EXTENSION);
    }

    @Test
    @Transactional
    void createFingerprintTemplateWithExistingId() throws Exception {
        // Create the FingerprintTemplate with an existing ID
        fingerprintTemplate.setId(1L);
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        int databaseSizeBeforeCreate = fingerprintTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFingerprintTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFingerprintTemplates() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        // Get all the fingerprintTemplateList
        restFingerprintTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fingerprintTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].templateVersion").value(hasItem(DEFAULT_TEMPLATE_VERSION)))
            .andExpect(jsonPath("$.[*].templateDataContentType").value(hasItem(DEFAULT_TEMPLATE_DATA_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].templateData").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_TEMPLATE_DATA))))
            .andExpect(jsonPath("$.[*].originalImageContentType").value(hasItem(DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].originalImage").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_ORIGINAL_IMAGE))))
            .andExpect(jsonPath("$.[*].originalImageMime").value(hasItem(DEFAULT_ORIGINAL_IMAGE_MIME)))
            .andExpect(jsonPath("$.[*].originalImageExtension").value(hasItem(DEFAULT_ORIGINAL_IMAGE_EXTENSION)));
    }

    @Test
    @Transactional
    void getFingerprintTemplate() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        // Get the fingerprintTemplate
        restFingerprintTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, fingerprintTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fingerprintTemplate.getId().intValue()))
            .andExpect(jsonPath("$.templateVersion").value(DEFAULT_TEMPLATE_VERSION))
            .andExpect(jsonPath("$.templateDataContentType").value(DEFAULT_TEMPLATE_DATA_CONTENT_TYPE))
            .andExpect(jsonPath("$.templateData").value(Base64.getEncoder().encodeToString(DEFAULT_TEMPLATE_DATA)))
            .andExpect(jsonPath("$.originalImageContentType").value(DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.originalImage").value(Base64.getEncoder().encodeToString(DEFAULT_ORIGINAL_IMAGE)))
            .andExpect(jsonPath("$.originalImageMime").value(DEFAULT_ORIGINAL_IMAGE_MIME))
            .andExpect(jsonPath("$.originalImageExtension").value(DEFAULT_ORIGINAL_IMAGE_EXTENSION));
    }

    @Test
    @Transactional
    void getNonExistingFingerprintTemplate() throws Exception {
        // Get the fingerprintTemplate
        restFingerprintTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFingerprintTemplate() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();

        // Update the fingerprintTemplate
        FingerprintTemplate updatedFingerprintTemplate = fingerprintTemplateRepository.findById(fingerprintTemplate.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFingerprintTemplate are not directly saved in db
        em.detach(updatedFingerprintTemplate);
        updatedFingerprintTemplate
            .templateVersion(UPDATED_TEMPLATE_VERSION)
            .templateData(UPDATED_TEMPLATE_DATA)
            .templateDataContentType(UPDATED_TEMPLATE_DATA_CONTENT_TYPE)
            .originalImage(UPDATED_ORIGINAL_IMAGE)
            .originalImageContentType(UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE)
            .originalImageMime(UPDATED_ORIGINAL_IMAGE_MIME)
            .originalImageExtension(UPDATED_ORIGINAL_IMAGE_EXTENSION);
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(updatedFingerprintTemplate);

        restFingerprintTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fingerprintTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
        FingerprintTemplate testFingerprintTemplate = fingerprintTemplateList.get(fingerprintTemplateList.size() - 1);
        assertThat(testFingerprintTemplate.getTemplateVersion()).isEqualTo(UPDATED_TEMPLATE_VERSION);
        assertThat(testFingerprintTemplate.getTemplateData()).isEqualTo(UPDATED_TEMPLATE_DATA);
        assertThat(testFingerprintTemplate.getTemplateDataContentType()).isEqualTo(UPDATED_TEMPLATE_DATA_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImage()).isEqualTo(UPDATED_ORIGINAL_IMAGE);
        assertThat(testFingerprintTemplate.getOriginalImageContentType()).isEqualTo(UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImageMime()).isEqualTo(UPDATED_ORIGINAL_IMAGE_MIME);
        assertThat(testFingerprintTemplate.getOriginalImageExtension()).isEqualTo(UPDATED_ORIGINAL_IMAGE_EXTENSION);
    }

    @Test
    @Transactional
    void putNonExistingFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fingerprintTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFingerprintTemplateWithPatch() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();

        // Update the fingerprintTemplate using partial update
        FingerprintTemplate partialUpdatedFingerprintTemplate = new FingerprintTemplate();
        partialUpdatedFingerprintTemplate.setId(fingerprintTemplate.getId());

        partialUpdatedFingerprintTemplate
            .templateData(UPDATED_TEMPLATE_DATA)
            .templateDataContentType(UPDATED_TEMPLATE_DATA_CONTENT_TYPE)
            .originalImageExtension(UPDATED_ORIGINAL_IMAGE_EXTENSION);

        restFingerprintTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFingerprintTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFingerprintTemplate))
            )
            .andExpect(status().isOk());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
        FingerprintTemplate testFingerprintTemplate = fingerprintTemplateList.get(fingerprintTemplateList.size() - 1);
        assertThat(testFingerprintTemplate.getTemplateVersion()).isEqualTo(DEFAULT_TEMPLATE_VERSION);
        assertThat(testFingerprintTemplate.getTemplateData()).isEqualTo(UPDATED_TEMPLATE_DATA);
        assertThat(testFingerprintTemplate.getTemplateDataContentType()).isEqualTo(UPDATED_TEMPLATE_DATA_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImage()).isEqualTo(DEFAULT_ORIGINAL_IMAGE);
        assertThat(testFingerprintTemplate.getOriginalImageContentType()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImageMime()).isEqualTo(DEFAULT_ORIGINAL_IMAGE_MIME);
        assertThat(testFingerprintTemplate.getOriginalImageExtension()).isEqualTo(UPDATED_ORIGINAL_IMAGE_EXTENSION);
    }

    @Test
    @Transactional
    void fullUpdateFingerprintTemplateWithPatch() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();

        // Update the fingerprintTemplate using partial update
        FingerprintTemplate partialUpdatedFingerprintTemplate = new FingerprintTemplate();
        partialUpdatedFingerprintTemplate.setId(fingerprintTemplate.getId());

        partialUpdatedFingerprintTemplate
            .templateVersion(UPDATED_TEMPLATE_VERSION)
            .templateData(UPDATED_TEMPLATE_DATA)
            .templateDataContentType(UPDATED_TEMPLATE_DATA_CONTENT_TYPE)
            .originalImage(UPDATED_ORIGINAL_IMAGE)
            .originalImageContentType(UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE)
            .originalImageMime(UPDATED_ORIGINAL_IMAGE_MIME)
            .originalImageExtension(UPDATED_ORIGINAL_IMAGE_EXTENSION);

        restFingerprintTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFingerprintTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFingerprintTemplate))
            )
            .andExpect(status().isOk());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
        FingerprintTemplate testFingerprintTemplate = fingerprintTemplateList.get(fingerprintTemplateList.size() - 1);
        assertThat(testFingerprintTemplate.getTemplateVersion()).isEqualTo(UPDATED_TEMPLATE_VERSION);
        assertThat(testFingerprintTemplate.getTemplateData()).isEqualTo(UPDATED_TEMPLATE_DATA);
        assertThat(testFingerprintTemplate.getTemplateDataContentType()).isEqualTo(UPDATED_TEMPLATE_DATA_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImage()).isEqualTo(UPDATED_ORIGINAL_IMAGE);
        assertThat(testFingerprintTemplate.getOriginalImageContentType()).isEqualTo(UPDATED_ORIGINAL_IMAGE_CONTENT_TYPE);
        assertThat(testFingerprintTemplate.getOriginalImageMime()).isEqualTo(UPDATED_ORIGINAL_IMAGE_MIME);
        assertThat(testFingerprintTemplate.getOriginalImageExtension()).isEqualTo(UPDATED_ORIGINAL_IMAGE_EXTENSION);
    }

    @Test
    @Transactional
    void patchNonExistingFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fingerprintTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFingerprintTemplate() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintTemplateRepository.findAll().size();
        fingerprintTemplate.setId(longCount.incrementAndGet());

        // Create the FingerprintTemplate
        FingerprintTemplateDTO fingerprintTemplateDTO = fingerprintTemplateMapper.toDto(fingerprintTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fingerprintTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the FingerprintTemplate in the database
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFingerprintTemplate() throws Exception {
        // Initialize the database
        fingerprintTemplateRepository.saveAndFlush(fingerprintTemplate);

        int databaseSizeBeforeDelete = fingerprintTemplateRepository.findAll().size();

        // Delete the fingerprintTemplate
        restFingerprintTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, fingerprintTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<FingerprintTemplate> fingerprintTemplateList = fingerprintTemplateRepository.findAll();
        assertThat(fingerprintTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
