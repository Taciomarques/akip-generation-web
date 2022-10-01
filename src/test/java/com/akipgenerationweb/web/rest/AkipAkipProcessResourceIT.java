package com.akipgenerationweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akipgenerationweb.IntegrationTest;
import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.akipgenerationweb.repository.AkipProcessRepository;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
import com.akipgenerationweb.service.mapper.AkipProcessMapper;
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
 * Integration tests for the {@link AkipProcessResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkipAkipProcessResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_PERCENTAGE_EXECUTED = 1;
    private static final Integer UPDATED_PERCENTAGE_EXECUTED = 2;

    private static final StatusProcess DEFAULT_STATUS = StatusProcess.WAITING_GENERATE_DOMAIN_ENTITY;
    private static final StatusProcess UPDATED_STATUS = StatusProcess.WAITING_GENERATE_PROCESS_BINDING;

    private static final String ENTITY_API_URL = "/api/processes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AkipProcessRepository akipProcessRepository;

    @Autowired
    private AkipProcessMapper akipProcessMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProcessMockMvc;

    private AkipProcess process;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipProcess createEntity(EntityManager em) {
        AkipProcess process = new AkipProcess().name(DEFAULT_NAME).percentageExecuted(DEFAULT_PERCENTAGE_EXECUTED).status(DEFAULT_STATUS);
        return process;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipProcess createUpdatedEntity(EntityManager em) {
        AkipProcess process = new AkipProcess().name(UPDATED_NAME).percentageExecuted(UPDATED_PERCENTAGE_EXECUTED).status(UPDATED_STATUS);
        return process;
    }

    @BeforeEach
    public void initTest() {
        process = createEntity(em);
    }

    @Test
    @Transactional
    void createProcess() throws Exception {
        int databaseSizeBeforeCreate = akipProcessRepository.findAll().size();
        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);
        restProcessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isCreated());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeCreate + 1);
        AkipProcess testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProcess.getPercentageExecuted()).isEqualTo(DEFAULT_PERCENTAGE_EXECUTED);
        assertThat(testProcess.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void createProcessWithExistingId() throws Exception {
        // Create the Process with an existing ID
        process.setId(1L);
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        int databaseSizeBeforeCreate = akipProcessRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProcessMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProcesses() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        // Get all the processList
        restProcessMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(process.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].percentageExecuted").value(hasItem(DEFAULT_PERCENTAGE_EXECUTED)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    void getProcess() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        // Get the process
        restProcessMockMvc
            .perform(get(ENTITY_API_URL_ID, process.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(process.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.percentageExecuted").value(DEFAULT_PERCENTAGE_EXECUTED))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProcess() throws Exception {
        // Get the process
        restProcessMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProcess() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();

        // Update the process
        AkipProcess updatedProcess = akipProcessRepository.findById(process.getId()).get();
        // Disconnect from session so that the updates on updatedProcess are not directly saved in db
        em.detach(updatedProcess);
        updatedProcess.name(UPDATED_NAME).percentageExecuted(UPDATED_PERCENTAGE_EXECUTED).status(UPDATED_STATUS);
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(updatedProcess);

        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipProcessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        AkipProcess testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcess.getPercentageExecuted()).isEqualTo(UPDATED_PERCENTAGE_EXECUTED);
        assertThat(testProcess.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void putNonExistingProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipProcessDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipProcessDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProcessWithPatch() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();

        // Update the process using partial update
        AkipProcess partialUpdatedProcess = new AkipProcess();
        partialUpdatedProcess.setId(process.getId());

        partialUpdatedProcess.name(UPDATED_NAME);

        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcess))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        AkipProcess testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcess.getPercentageExecuted()).isEqualTo(DEFAULT_PERCENTAGE_EXECUTED);
        assertThat(testProcess.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    void fullUpdateProcessWithPatch() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();

        // Update the process using partial update
        AkipProcess partialUpdatedProcess = new AkipProcess();
        partialUpdatedProcess.setId(process.getId());

        partialUpdatedProcess.name(UPDATED_NAME).percentageExecuted(UPDATED_PERCENTAGE_EXECUTED).status(UPDATED_STATUS);

        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProcess.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProcess))
            )
            .andExpect(status().isOk());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
        AkipProcess testProcess = processList.get(processList.size() - 1);
        assertThat(testProcess.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProcess.getPercentageExecuted()).isEqualTo(UPDATED_PERCENTAGE_EXECUTED);
        assertThat(testProcess.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    void patchNonExistingProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akipProcessDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProcess() throws Exception {
        int databaseSizeBeforeUpdate = akipProcessRepository.findAll().size();
        process.setId(count.incrementAndGet());

        // Create the Process
        AkipProcessDTO akipProcessDTO = akipProcessMapper.toDto(process);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProcessMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(akipProcessDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Process in the database
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProcess() throws Exception {
        // Initialize the database
        akipProcessRepository.saveAndFlush(process);

        int databaseSizeBeforeDelete = akipProcessRepository.findAll().size();

        // Delete the process
        restProcessMockMvc
            .perform(delete(ENTITY_API_URL_ID, process.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AkipProcess> processList = akipProcessRepository.findAll();
        assertThat(processList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
