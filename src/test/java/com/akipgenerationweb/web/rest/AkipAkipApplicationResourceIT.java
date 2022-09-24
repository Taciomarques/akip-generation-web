package com.akipgenerationweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akipgenerationweb.IntegrationTest;
import com.akipgenerationweb.domain.AkipApplication;
import com.akipgenerationweb.repository.AkipApplicationRepository;
import com.akipgenerationweb.service.dto.AkipApplicationDTO;
import com.akipgenerationweb.service.mapper.AkipApplicationMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link AkipApplicationResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkipAkipApplicationResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_REPOSITORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_REPOSITORY_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_CREATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_PROPERTIES = "AAAAAAAAAA";
    private static final String UPDATED_PROPERTIES = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/applications";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AkipApplicationRepository applicationRepository;

    @Autowired
    private AkipApplicationMapper akipApplicationMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restApplicationMockMvc;

    private AkipApplication akipApplication;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipApplication createEntity(EntityManager em) {
        AkipApplication akipApplication = new AkipApplication()
            .name(DEFAULT_NAME)
            .repositoryName(DEFAULT_REPOSITORY_NAME)
            .createDate(DEFAULT_CREATE_DATE)
            .properties(DEFAULT_PROPERTIES);
        return akipApplication;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipApplication createUpdatedEntity(EntityManager em) {
        AkipApplication akipApplication = new AkipApplication()
            .name(UPDATED_NAME)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .properties(UPDATED_PROPERTIES);
        return akipApplication;
    }

    @BeforeEach
    public void initTest() {
        akipApplication = createEntity(em);
    }

    @Test
    @Transactional
    void createApplication() throws Exception {
        int databaseSizeBeforeCreate = applicationRepository.findAll().size();
        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);
        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeCreate + 1);
        AkipApplication testAkipApplication = akipApplicationList.get(akipApplicationList.size() - 1);
        assertThat(testAkipApplication.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAkipApplication.getRepositoryName()).isEqualTo(DEFAULT_REPOSITORY_NAME);
        assertThat(testAkipApplication.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAkipApplication.getProperties()).isEqualTo(DEFAULT_PROPERTIES);
    }

    @Test
    @Transactional
    void createApplicationWithExistingId() throws Exception {
        // Create the Application with an existing ID
        akipApplication.setId(1L);
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        int databaseSizeBeforeCreate = applicationRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restApplicationMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllApplications() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        // Get all the applicationList
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akipApplication.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].repositoryName").value(hasItem(DEFAULT_REPOSITORY_NAME)))
            .andExpect(jsonPath("$.[*].createDate").value(hasItem(DEFAULT_CREATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].properties").value(hasItem(DEFAULT_PROPERTIES)));
    }

    @Test
    @Transactional
    void getApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        // Get the application
        restApplicationMockMvc
            .perform(get(ENTITY_API_URL_ID, akipApplication.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(akipApplication.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.repositoryName").value(DEFAULT_REPOSITORY_NAME))
            .andExpect(jsonPath("$.createDate").value(DEFAULT_CREATE_DATE.toString()))
            .andExpect(jsonPath("$.properties").value(DEFAULT_PROPERTIES));
    }

    @Test
    @Transactional
    void getNonExistingApplication() throws Exception {
        // Get the application
        restApplicationMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application
        AkipApplication updatedAkipApplication = applicationRepository.findById(akipApplication.getId()).get();
        // Disconnect from session so that the updates on updatedApplication are not directly saved in db
        em.detach(updatedAkipApplication);
        updatedAkipApplication
            .name(UPDATED_NAME)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .properties(UPDATED_PROPERTIES);
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(updatedAkipApplication);

        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
        AkipApplication testAkipApplication = akipApplicationList.get(akipApplicationList.size() - 1);
        assertThat(testAkipApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipApplication.getRepositoryName()).isEqualTo(UPDATED_REPOSITORY_NAME);
        assertThat(testAkipApplication.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAkipApplication.getProperties()).isEqualTo(UPDATED_PROPERTIES);
    }

    @Test
    @Transactional
    void putNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipApplicationDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        AkipApplication partialUpdatedAkipApplication = new AkipApplication();
        partialUpdatedAkipApplication.setId(akipApplication.getId());

        partialUpdatedAkipApplication.name(UPDATED_NAME).properties(UPDATED_PROPERTIES);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkipApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkipApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
        AkipApplication testAkipApplication = akipApplicationList.get(akipApplicationList.size() - 1);
        assertThat(testAkipApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipApplication.getRepositoryName()).isEqualTo(DEFAULT_REPOSITORY_NAME);
        assertThat(testAkipApplication.getCreateDate()).isEqualTo(DEFAULT_CREATE_DATE);
        assertThat(testAkipApplication.getProperties()).isEqualTo(UPDATED_PROPERTIES);
    }

    @Test
    @Transactional
    void fullUpdateApplicationWithPatch() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();

        // Update the application using partial update
        AkipApplication partialUpdatedAkipApplication = new AkipApplication();
        partialUpdatedAkipApplication.setId(akipApplication.getId());

        partialUpdatedAkipApplication
            .name(UPDATED_NAME)
            .repositoryName(UPDATED_REPOSITORY_NAME)
            .createDate(UPDATED_CREATE_DATE)
            .properties(UPDATED_PROPERTIES);

        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkipApplication.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkipApplication))
            )
            .andExpect(status().isOk());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
        AkipApplication testAkipApplication = akipApplicationList.get(akipApplicationList.size() - 1);
        assertThat(testAkipApplication.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipApplication.getRepositoryName()).isEqualTo(UPDATED_REPOSITORY_NAME);
        assertThat(testAkipApplication.getCreateDate()).isEqualTo(UPDATED_CREATE_DATE);
        assertThat(testAkipApplication.getProperties()).isEqualTo(UPDATED_PROPERTIES);
    }

    @Test
    @Transactional
    void patchNonExistingApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akipApplicationDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamApplication() throws Exception {
        int databaseSizeBeforeUpdate = applicationRepository.findAll().size();
        akipApplication.setId(count.incrementAndGet());

        // Create the Application
        AkipApplicationDTO akipApplicationDTO = akipApplicationMapper.toDto(akipApplication);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restApplicationMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipApplicationDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Application in the database
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteApplication() throws Exception {
        // Initialize the database
        applicationRepository.saveAndFlush(akipApplication);

        int databaseSizeBeforeDelete = applicationRepository.findAll().size();

        // Delete the application
        restApplicationMockMvc
            .perform(delete(ENTITY_API_URL_ID, akipApplication.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AkipApplication> akipApplicationList = applicationRepository.findAll();
        assertThat(akipApplicationList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
