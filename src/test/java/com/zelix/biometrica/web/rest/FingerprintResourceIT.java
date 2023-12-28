package com.zelix.biometrica.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.zelix.biometrica.IntegrationTest;
import com.zelix.biometrica.domain.Fingerprint;
import com.zelix.biometrica.domain.enumeration.FingerName;
import com.zelix.biometrica.domain.enumeration.HandName;
import com.zelix.biometrica.repository.FingerprintRepository;
import jakarta.persistence.EntityManager;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
 * Integration tests for the {@link FingerprintResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FingerprintResourceIT {

    private static final UUID DEFAULT_UUID = UUID.randomUUID();
    private static final UUID UPDATED_UUID = UUID.randomUUID();

    private static final FingerName DEFAULT_FINGER_NAME = FingerName.PINKY_FINGER;
    private static final FingerName UPDATED_FINGER_NAME = FingerName.RING_FINGER;

    private static final HandName DEFAULT_HAND_NAME = HandName.RIGHT_HAND;
    private static final HandName UPDATED_HAND_NAME = HandName.LEFT_HAND;

    private static final String ENTITY_API_URL = "/api/fingerprints";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private FingerprintRepository fingerprintRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFingerprintMockMvc;

    private Fingerprint fingerprint;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fingerprint createEntity(EntityManager em) {
        Fingerprint fingerprint = new Fingerprint().uuid(DEFAULT_UUID).fingerName(DEFAULT_FINGER_NAME).handName(DEFAULT_HAND_NAME);
        return fingerprint;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fingerprint createUpdatedEntity(EntityManager em) {
        Fingerprint fingerprint = new Fingerprint().uuid(UPDATED_UUID).fingerName(UPDATED_FINGER_NAME).handName(UPDATED_HAND_NAME);
        return fingerprint;
    }

    @BeforeEach
    public void initTest() {
        fingerprint = createEntity(em);
    }

    @Test
    @Transactional
    void createFingerprint() throws Exception {
        int databaseSizeBeforeCreate = fingerprintRepository.findAll().size();
        // Create the Fingerprint
        restFingerprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isCreated());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeCreate + 1);
        Fingerprint testFingerprint = fingerprintList.get(fingerprintList.size() - 1);
        assertThat(testFingerprint.getUuid()).isEqualTo(DEFAULT_UUID);
        assertThat(testFingerprint.getFingerName()).isEqualTo(DEFAULT_FINGER_NAME);
        assertThat(testFingerprint.getHandName()).isEqualTo(DEFAULT_HAND_NAME);
    }

    @Test
    @Transactional
    void createFingerprintWithExistingId() throws Exception {
        // Create the Fingerprint with an existing ID
        fingerprint.setId(1L);

        int databaseSizeBeforeCreate = fingerprintRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFingerprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isBadRequest());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkUuidIsRequired() throws Exception {
        int databaseSizeBeforeTest = fingerprintRepository.findAll().size();
        // set the field null
        fingerprint.setUuid(null);

        // Create the Fingerprint, which fails.

        restFingerprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isBadRequest());

        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFingerNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fingerprintRepository.findAll().size();
        // set the field null
        fingerprint.setFingerName(null);

        // Create the Fingerprint, which fails.

        restFingerprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isBadRequest());

        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkHandNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fingerprintRepository.findAll().size();
        // set the field null
        fingerprint.setHandName(null);

        // Create the Fingerprint, which fails.

        restFingerprintMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isBadRequest());

        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllFingerprints() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        // Get all the fingerprintList
        restFingerprintMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fingerprint.getId().intValue())))
            .andExpect(jsonPath("$.[*].uuid").value(hasItem(DEFAULT_UUID.toString())))
            .andExpect(jsonPath("$.[*].fingerName").value(hasItem(DEFAULT_FINGER_NAME.toString())))
            .andExpect(jsonPath("$.[*].handName").value(hasItem(DEFAULT_HAND_NAME.toString())));
    }

    @Test
    @Transactional
    void getFingerprint() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        // Get the fingerprint
        restFingerprintMockMvc
            .perform(get(ENTITY_API_URL_ID, fingerprint.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fingerprint.getId().intValue()))
            .andExpect(jsonPath("$.uuid").value(DEFAULT_UUID.toString()))
            .andExpect(jsonPath("$.fingerName").value(DEFAULT_FINGER_NAME.toString()))
            .andExpect(jsonPath("$.handName").value(DEFAULT_HAND_NAME.toString()));
    }

    @Test
    @Transactional
    void getNonExistingFingerprint() throws Exception {
        // Get the fingerprint
        restFingerprintMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFingerprint() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();

        // Update the fingerprint
        Fingerprint updatedFingerprint = fingerprintRepository.findById(fingerprint.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFingerprint are not directly saved in db
        em.detach(updatedFingerprint);
        updatedFingerprint.uuid(UPDATED_UUID).fingerName(UPDATED_FINGER_NAME).handName(UPDATED_HAND_NAME);

        restFingerprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFingerprint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updatedFingerprint))
            )
            .andExpect(status().isOk());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
        Fingerprint testFingerprint = fingerprintList.get(fingerprintList.size() - 1);
        assertThat(testFingerprint.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFingerprint.getFingerName()).isEqualTo(UPDATED_FINGER_NAME);
        assertThat(testFingerprint.getHandName()).isEqualTo(UPDATED_HAND_NAME);
    }

    @Test
    @Transactional
    void putNonExistingFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fingerprint.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(fingerprint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(fingerprint)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFingerprintWithPatch() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();

        // Update the fingerprint using partial update
        Fingerprint partialUpdatedFingerprint = new Fingerprint();
        partialUpdatedFingerprint.setId(fingerprint.getId());

        partialUpdatedFingerprint.uuid(UPDATED_UUID).fingerName(UPDATED_FINGER_NAME);

        restFingerprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFingerprint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFingerprint))
            )
            .andExpect(status().isOk());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
        Fingerprint testFingerprint = fingerprintList.get(fingerprintList.size() - 1);
        assertThat(testFingerprint.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFingerprint.getFingerName()).isEqualTo(UPDATED_FINGER_NAME);
        assertThat(testFingerprint.getHandName()).isEqualTo(DEFAULT_HAND_NAME);
    }

    @Test
    @Transactional
    void fullUpdateFingerprintWithPatch() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();

        // Update the fingerprint using partial update
        Fingerprint partialUpdatedFingerprint = new Fingerprint();
        partialUpdatedFingerprint.setId(fingerprint.getId());

        partialUpdatedFingerprint.uuid(UPDATED_UUID).fingerName(UPDATED_FINGER_NAME).handName(UPDATED_HAND_NAME);

        restFingerprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFingerprint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedFingerprint))
            )
            .andExpect(status().isOk());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
        Fingerprint testFingerprint = fingerprintList.get(fingerprintList.size() - 1);
        assertThat(testFingerprint.getUuid()).isEqualTo(UPDATED_UUID);
        assertThat(testFingerprint.getFingerName()).isEqualTo(UPDATED_FINGER_NAME);
        assertThat(testFingerprint.getHandName()).isEqualTo(UPDATED_HAND_NAME);
    }

    @Test
    @Transactional
    void patchNonExistingFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fingerprint.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fingerprint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(fingerprint))
            )
            .andExpect(status().isBadRequest());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFingerprint() throws Exception {
        int databaseSizeBeforeUpdate = fingerprintRepository.findAll().size();
        fingerprint.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFingerprintMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(fingerprint))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Fingerprint in the database
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFingerprint() throws Exception {
        // Initialize the database
        fingerprintRepository.saveAndFlush(fingerprint);

        int databaseSizeBeforeDelete = fingerprintRepository.findAll().size();

        // Delete the fingerprint
        restFingerprintMockMvc
            .perform(delete(ENTITY_API_URL_ID, fingerprint.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Fingerprint> fingerprintList = fingerprintRepository.findAll();
        assertThat(fingerprintList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
