package com.akipgenerationweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akipgenerationweb.IntegrationTest;
import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.AkipEntityRepository;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.mapper.AkipEntityMapper;
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
 * Integration tests for the {@link AkipEntityResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AkipEntityResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_FIELDS = "AAAAAAAAAA";
    private static final String UPDATED_FIELDS = "BBBBBBBBBB";

    private static final String DEFAULT_RELATIONS = "AAAAAAAAAA";
    private static final String UPDATED_RELATIONS = "BBBBBBBBBB";

    private static final TypeEntity DEFAULT_TYPE = TypeEntity.DOMAIN;
    private static final TypeEntity UPDATED_TYPE = TypeEntity.PROCESS_BINDING;

    private static final String ENTITY_API_URL = "/api/entidades";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AkipEntityRepository akipEntityRepository;

    @Autowired
    private AkipEntityMapper akipEntityMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntidadeMockMvc;

    private AkipEntity akipEntity;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipEntity createEntity(EntityManager em) {
        AkipEntity akipEntity = new AkipEntity().name(DEFAULT_NAME).fields(DEFAULT_FIELDS).relations(DEFAULT_RELATIONS).type(DEFAULT_TYPE);
        return akipEntity;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AkipEntity createUpdatedEntity(EntityManager em) {
        AkipEntity akipEntity = new AkipEntity().name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);
        return akipEntity;
    }

    @BeforeEach
    public void initTest() {
        akipEntity = createEntity(em);
    }

    @Test
    @Transactional
    void createEntidade() throws Exception {
        int databaseSizeBeforeCreate = akipEntityRepository.findAll().size();
        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);
        restEntidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipEntityDTO)))
            .andExpect(status().isCreated());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeCreate + 1);
        AkipEntity testAkipEntity = akipEntityList.get(akipEntityList.size() - 1);
        assertThat(testAkipEntity.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAkipEntity.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testAkipEntity.getRelations()).isEqualTo(DEFAULT_RELATIONS);
        assertThat(testAkipEntity.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEntidadeWithExistingId() throws Exception {
        // Create the Entidade with an existing ID
        akipEntity.setId(1L);
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        int databaseSizeBeforeCreate = akipEntityRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipEntityDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntidades() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        // Get all the entidadeList
        restEntidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(akipEntity.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS)))
            .andExpect(jsonPath("$.[*].relations").value(hasItem(DEFAULT_RELATIONS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEntidade() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        // Get the entidade
        restEntidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, akipEntity.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(akipEntity.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.fields").value(DEFAULT_FIELDS))
            .andExpect(jsonPath("$.relations").value(DEFAULT_RELATIONS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingEntidade() throws Exception {
        // Get the entidade
        restEntidadeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewEntidade() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();

        // Update the entidade
        AkipEntity updatedAkipEntity = akipEntityRepository.findById(akipEntity.getId()).get();
        // Disconnect from session so that the updates on updatedEntidade are not directly saved in db
        em.detach(updatedAkipEntity);
        updatedAkipEntity.name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(updatedAkipEntity);

        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
        AkipEntity testAkipEntity = akipEntityList.get(akipEntityList.size() - 1);
        assertThat(testAkipEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipEntity.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testAkipEntity.getRelations()).isEqualTo(UPDATED_RELATIONS);
        assertThat(testAkipEntity.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, akipEntityDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(akipEntityDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntidadeWithPatch() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();

        // Update the entidade using partial update
        AkipEntity partialUpdatedAkipEntity = new AkipEntity();
        partialUpdatedAkipEntity.setId(akipEntity.getId());

        partialUpdatedAkipEntity.name(UPDATED_NAME).type(UPDATED_TYPE);

        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkipEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkipEntity))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
        AkipEntity testAkipEntity = akipEntityList.get(akipEntityList.size() - 1);
        assertThat(testAkipEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipEntity.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testAkipEntity.getRelations()).isEqualTo(DEFAULT_RELATIONS);
        assertThat(testAkipEntity.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEntidadeWithPatch() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();

        // Update the entidade using partial update
        AkipEntity partialUpdatedAkipEntity = new AkipEntity();
        partialUpdatedAkipEntity.setId(akipEntity.getId());

        partialUpdatedAkipEntity.name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);

        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAkipEntity.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAkipEntity))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
        AkipEntity testAkipEntity = akipEntityList.get(akipEntityList.size() - 1);
        assertThat(testAkipEntity.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAkipEntity.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testAkipEntity.getRelations()).isEqualTo(UPDATED_RELATIONS);
        assertThat(testAkipEntity.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, akipEntityDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntidade() throws Exception {
        int databaseSizeBeforeUpdate = akipEntityRepository.findAll().size();
        akipEntity.setId(count.incrementAndGet());

        // Create the Entidade
        AkipEntityDTO akipEntityDTO = akipEntityMapper.toDto(akipEntity);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(akipEntityDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidade in the database
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntidade() throws Exception {
        // Initialize the database
        akipEntityRepository.saveAndFlush(akipEntity);

        int databaseSizeBeforeDelete = akipEntityRepository.findAll().size();

        // Delete the entidade
        restEntidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, akipEntity.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AkipEntity> akipEntityList = akipEntityRepository.findAll();
        assertThat(akipEntityList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
