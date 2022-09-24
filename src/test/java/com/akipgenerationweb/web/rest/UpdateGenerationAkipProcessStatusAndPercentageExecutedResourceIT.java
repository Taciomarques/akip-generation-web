package com.akipgenerationweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akipgenerationweb.IntegrationTest;
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
 * Integration tests for the {@link UpdateGenerationProcessStatusAndPercentageExecutedResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class UpdateGenerationAkipProcessStatusAndPercentageExecutedResourceIT {

    private static final String ENTITY_API_URL = "/api/update-generation-process-status-and-percentage-executeds";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private UpdateGenerationProcessStatusAndPercentageExecutedRepository updateGenerationProcessStatusAndPercentageExecutedRepository;

    @Autowired
    private UpdateGenerationProcessStatusAndPercentageExecutedMapper updateGenerationProcessStatusAndPercentageExecutedMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc;

    private UpdateGenerationProcessStatusAndPercentageExecuted updateGenerationProcessStatusAndPercentageExecuted;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpdateGenerationProcessStatusAndPercentageExecuted createEntity(EntityManager em) {
        UpdateGenerationProcessStatusAndPercentageExecuted updateGenerationProcessStatusAndPercentageExecuted = new UpdateGenerationProcessStatusAndPercentageExecuted();
        return updateGenerationProcessStatusAndPercentageExecuted;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UpdateGenerationProcessStatusAndPercentageExecuted createUpdatedEntity(EntityManager em) {
        UpdateGenerationProcessStatusAndPercentageExecuted updateGenerationProcessStatusAndPercentageExecuted = new UpdateGenerationProcessStatusAndPercentageExecuted();
        return updateGenerationProcessStatusAndPercentageExecuted;
    }

    @BeforeEach
    public void initTest() {
        updateGenerationProcessStatusAndPercentageExecuted = createEntity(em);
    }

    @Test
    @Transactional
    void createUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeCreate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isCreated());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeCreate + 1);
        UpdateGenerationProcessStatusAndPercentageExecuted testUpdateGenerationProcessStatusAndPercentageExecuted = updateGenerationProcessStatusAndPercentageExecutedList.get(
            updateGenerationProcessStatusAndPercentageExecutedList.size() - 1
        );
    }

    @Test
    @Transactional
    void createUpdateGenerationProcessStatusAndPercentageExecutedWithExistingId() throws Exception {
        // Create the UpdateGenerationProcessStatusAndPercentageExecuted with an existing ID
        updateGenerationProcessStatusAndPercentageExecuted.setId(1L);
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        int databaseSizeBeforeCreate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllUpdateGenerationProcessStatusAndPercentageExecuteds() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        // Get all the updateGenerationProcessStatusAndPercentageExecutedList
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(updateGenerationProcessStatusAndPercentageExecuted.getId().intValue())));
    }

    @Test
    @Transactional
    void getUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        // Get the updateGenerationProcessStatusAndPercentageExecuted
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(get(ENTITY_API_URL_ID, updateGenerationProcessStatusAndPercentageExecuted.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(updateGenerationProcessStatusAndPercentageExecuted.getId().intValue()));
    }

    @Test
    @Transactional
    void getNonExistingUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        // Get the updateGenerationProcessStatusAndPercentageExecuted
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();

        // Update the updateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecuted updatedUpdateGenerationProcessStatusAndPercentageExecuted = updateGenerationProcessStatusAndPercentageExecutedRepository
            .findById(updateGenerationProcessStatusAndPercentageExecuted.getId())
            .get();
        // Disconnect from session so that the updates on updatedUpdateGenerationProcessStatusAndPercentageExecuted are not directly saved in db
        em.detach(updatedUpdateGenerationProcessStatusAndPercentageExecuted);
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updatedUpdateGenerationProcessStatusAndPercentageExecuted
        );

        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updateGenerationProcessStatusAndPercentageExecutedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isOk());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
        UpdateGenerationProcessStatusAndPercentageExecuted testUpdateGenerationProcessStatusAndPercentageExecuted = updateGenerationProcessStatusAndPercentageExecutedList.get(
            updateGenerationProcessStatusAndPercentageExecutedList.size() - 1
        );
    }

    @Test
    @Transactional
    void putNonExistingUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updateGenerationProcessStatusAndPercentageExecutedDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateUpdateGenerationProcessStatusAndPercentageExecutedWithPatch() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();

        // Update the updateGenerationProcessStatusAndPercentageExecuted using partial update
        UpdateGenerationProcessStatusAndPercentageExecuted partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted = new UpdateGenerationProcessStatusAndPercentageExecuted();
        partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted.setId(updateGenerationProcessStatusAndPercentageExecuted.getId());

        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted))
            )
            .andExpect(status().isOk());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
        UpdateGenerationProcessStatusAndPercentageExecuted testUpdateGenerationProcessStatusAndPercentageExecuted = updateGenerationProcessStatusAndPercentageExecutedList.get(
            updateGenerationProcessStatusAndPercentageExecutedList.size() - 1
        );
    }

    @Test
    @Transactional
    void fullUpdateUpdateGenerationProcessStatusAndPercentageExecutedWithPatch() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();

        // Update the updateGenerationProcessStatusAndPercentageExecuted using partial update
        UpdateGenerationProcessStatusAndPercentageExecuted partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted = new UpdateGenerationProcessStatusAndPercentageExecuted();
        partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted.setId(updateGenerationProcessStatusAndPercentageExecuted.getId());

        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedUpdateGenerationProcessStatusAndPercentageExecuted))
            )
            .andExpect(status().isOk());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
        UpdateGenerationProcessStatusAndPercentageExecuted testUpdateGenerationProcessStatusAndPercentageExecuted = updateGenerationProcessStatusAndPercentageExecutedList.get(
            updateGenerationProcessStatusAndPercentageExecutedList.size() - 1
        );
    }

    @Test
    @Transactional
    void patchNonExistingUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, updateGenerationProcessStatusAndPercentageExecutedDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        int databaseSizeBeforeUpdate = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();
        updateGenerationProcessStatusAndPercentageExecuted.setId(count.incrementAndGet());

        // Create the UpdateGenerationProcessStatusAndPercentageExecuted
        UpdateGenerationProcessStatusAndPercentageExecutedDTO updateGenerationProcessStatusAndPercentageExecutedDTO = updateGenerationProcessStatusAndPercentageExecutedMapper.toDto(
            updateGenerationProcessStatusAndPercentageExecuted
        );

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(updateGenerationProcessStatusAndPercentageExecutedDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the UpdateGenerationProcessStatusAndPercentageExecuted in the database
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteUpdateGenerationProcessStatusAndPercentageExecuted() throws Exception {
        // Initialize the database
        updateGenerationProcessStatusAndPercentageExecutedRepository.saveAndFlush(updateGenerationProcessStatusAndPercentageExecuted);

        int databaseSizeBeforeDelete = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll().size();

        // Delete the updateGenerationProcessStatusAndPercentageExecuted
        restUpdateGenerationProcessStatusAndPercentageExecutedMockMvc
            .perform(
                delete(ENTITY_API_URL_ID, updateGenerationProcessStatusAndPercentageExecuted.getId()).accept(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UpdateGenerationProcessStatusAndPercentageExecuted> updateGenerationProcessStatusAndPercentageExecutedList = updateGenerationProcessStatusAndPercentageExecutedRepository.findAll();
        assertThat(updateGenerationProcessStatusAndPercentageExecutedList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
