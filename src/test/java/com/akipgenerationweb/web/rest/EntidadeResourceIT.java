package com.akipgenerationweb.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.akipgenerationweb.IntegrationTest;
import com.akipgenerationweb.domain.Entidade;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.EntidadeRepository;
import com.akipgenerationweb.service.dto.EntidadeDTO;
import com.akipgenerationweb.service.mapper.EntidadeMapper;
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
 * Integration tests for the {@link EntidadeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class EntidadeResourceIT {

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
    private EntidadeRepository entidadeRepository;

    @Autowired
    private EntidadeMapper entidadeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restEntidadeMockMvc;

    private Entidade entidade;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidade createEntity(EntityManager em) {
        Entidade entidade = new Entidade().name(DEFAULT_NAME).fields(DEFAULT_FIELDS).relations(DEFAULT_RELATIONS).type(DEFAULT_TYPE);
        return entidade;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Entidade createUpdatedEntity(EntityManager em) {
        Entidade entidade = new Entidade().name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);
        return entidade;
    }

    @BeforeEach
    public void initTest() {
        entidade = createEntity(em);
    }

    @Test
    @Transactional
    void createEntidade() throws Exception {
        int databaseSizeBeforeCreate = entidadeRepository.findAll().size();
        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);
        restEntidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isCreated());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeCreate + 1);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testEntidade.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testEntidade.getRelations()).isEqualTo(DEFAULT_RELATIONS);
        assertThat(testEntidade.getType()).isEqualTo(DEFAULT_TYPE);
    }

    @Test
    @Transactional
    void createEntidadeWithExistingId() throws Exception {
        // Create the Entidade with an existing ID
        entidade.setId(1L);
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        int databaseSizeBeforeCreate = entidadeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restEntidadeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllEntidades() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        // Get all the entidadeList
        restEntidadeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(entidade.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].fields").value(hasItem(DEFAULT_FIELDS)))
            .andExpect(jsonPath("$.[*].relations").value(hasItem(DEFAULT_RELATIONS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())));
    }

    @Test
    @Transactional
    void getEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        // Get the entidade
        restEntidadeMockMvc
            .perform(get(ENTITY_API_URL_ID, entidade.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(entidade.getId().intValue()))
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
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();

        // Update the entidade
        Entidade updatedEntidade = entidadeRepository.findById(entidade.getId()).get();
        // Disconnect from session so that the updates on updatedEntidade are not directly saved in db
        em.detach(updatedEntidade);
        updatedEntidade.name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(updatedEntidade);

        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntidade.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testEntidade.getRelations()).isEqualTo(UPDATED_RELATIONS);
        assertThat(testEntidade.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void putNonExistingEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, entidadeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(entidadeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateEntidadeWithPatch() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();

        // Update the entidade using partial update
        Entidade partialUpdatedEntidade = new Entidade();
        partialUpdatedEntidade.setId(entidade.getId());

        partialUpdatedEntidade.name(UPDATED_NAME).type(UPDATED_TYPE);

        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidade))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntidade.getFields()).isEqualTo(DEFAULT_FIELDS);
        assertThat(testEntidade.getRelations()).isEqualTo(DEFAULT_RELATIONS);
        assertThat(testEntidade.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void fullUpdateEntidadeWithPatch() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();

        // Update the entidade using partial update
        Entidade partialUpdatedEntidade = new Entidade();
        partialUpdatedEntidade.setId(entidade.getId());

        partialUpdatedEntidade.name(UPDATED_NAME).fields(UPDATED_FIELDS).relations(UPDATED_RELATIONS).type(UPDATED_TYPE);

        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedEntidade.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedEntidade))
            )
            .andExpect(status().isOk());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
        Entidade testEntidade = entidadeList.get(entidadeList.size() - 1);
        assertThat(testEntidade.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testEntidade.getFields()).isEqualTo(UPDATED_FIELDS);
        assertThat(testEntidade.getRelations()).isEqualTo(UPDATED_RELATIONS);
        assertThat(testEntidade.getType()).isEqualTo(UPDATED_TYPE);
    }

    @Test
    @Transactional
    void patchNonExistingEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, entidadeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamEntidade() throws Exception {
        int databaseSizeBeforeUpdate = entidadeRepository.findAll().size();
        entidade.setId(count.incrementAndGet());

        // Create the Entidade
        EntidadeDTO entidadeDTO = entidadeMapper.toDto(entidade);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restEntidadeMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(entidadeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Entidade in the database
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteEntidade() throws Exception {
        // Initialize the database
        entidadeRepository.saveAndFlush(entidade);

        int databaseSizeBeforeDelete = entidadeRepository.findAll().size();

        // Delete the entidade
        restEntidadeMockMvc
            .perform(delete(ENTITY_API_URL_ID, entidade.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Entidade> entidadeList = entidadeRepository.findAll();
        assertThat(entidadeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
