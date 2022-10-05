package com.akipgenerationweb.web.rest;

import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.AkipEntityRepository;
import com.akipgenerationweb.service.AkipEntityService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.web.rest.errors.BadRequestAlertException;
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
 * REST controller for managing {@link AkipEntity}.
 */
@RestController
@RequestMapping("/api")
public class AkipEntityResource {

    private final Logger log = LoggerFactory.getLogger(AkipEntityResource.class);

    private static final String ENTITY_NAME = "entidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkipEntityService akipEntityService;

    private final AkipEntityRepository akipEntityRepository;

    public AkipEntityResource(AkipEntityService akipEntityService, AkipEntityRepository akipEntityRepository) {
        this.akipEntityService = akipEntityService;
        this.akipEntityRepository = akipEntityRepository;
    }

    /**
     * {@code POST  /akip-entities} : Create a new akipEntity.
     *
     * @param akipEntityDTO the akipEntidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akipEntidadeDTO, or with status {@code 400 (Bad Request)} if the akipEntity has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akip-entities")
    public ResponseEntity<AkipEntityDTO> createAkipEntity(@RequestBody AkipEntityDTO akipEntityDTO) throws URISyntaxException {
        log.debug("REST request to save AkipEntity : {}", akipEntityDTO);
        if (akipEntityDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipEntityDTO result = akipEntityService.save(akipEntityDTO);
        return ResponseEntity
            .created(new URI("/api/akip-entity/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akip-entities/:id} : Updates an existing akipEntity.
     *
     * @param id the id of the akipEntityDTO to save.
     * @param akipEntityDTO the akipEntityDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipEntityDTO,
     * or with status {@code 400 (Bad Request)} if the akipEntityDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akipEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akip-entities/{id}")
    public ResponseEntity<AkipEntityDTO> updateAkipEntity(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipEntityDTO akipEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkipEntity : {}, {}", id, akipEntityDTO);
        if (akipEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipEntityDTO result = akipEntityService.save(akipEntityDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipEntityDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akip-entities/:id} : Partial updates given fields of an existing akipEntity, field will ignore if it is null
     *
     * @param id the id of the akipEntityDTO to save.
     * @param akipEntityDTO the akipEntidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipEntityDTO,
     * or with status {@code 400 (Bad Request)} if the akipEntityDTO is not valid,
     * or with status {@code 404 (Not Found)} if the akipEntityDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the akipEntityDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/akip-entities/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AkipEntityDTO> partialUpdateEntidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipEntityDTO akipEntityDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AkipEntity partially : {}, {}", id, akipEntityDTO);
        if (akipEntityDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipEntityDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipEntityRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkipEntityDTO> result = akipEntityService.partialUpdate(akipEntityDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipEntityDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /akip-entities} : get all the akipEntities by applicationId.
     * @param applicationId the id of the application the list akipEntities.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipEntities in body.
     */
    @GetMapping("/akip-entities/findByApplicationAndTypeEntity/{applicationId}/{typeEntity}")
    public List<AkipEntityDTO> getAllAkipEntities(@PathVariable Long applicationId, @PathVariable String typeEntity) {
        log.debug("REST request to get all AkipEntities by ApplicationId {} and TypeEntity {}", applicationId, typeEntity);
        return akipEntityService.findByApplicationIdAndTypeEntity(applicationId, TypeEntity.valueOf(typeEntity));
    }

    /**
     * {@code GET  /akip-entities} : get all the akipEntities.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipEntities in body.
     */
    @GetMapping("/akip-entities")
    public List<AkipEntityDTO> getAllAkipEntities() {
        log.debug("REST request to get all AkipEntities");
        return akipEntityService.findAll();
    }

    /**
     * {@code GET  /akip-entities/:id} : get the "id" akipEntity.
     *
     * @param id the id of the akipEntityDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akipEntityDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akip-entities/{id}")
    public ResponseEntity<AkipEntityDTO> getAkipEntity(@PathVariable Long id) {
        log.debug("REST request to get AkipEntity : {}", id);
        Optional<AkipEntityDTO> akipEntityDTO = akipEntityService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akipEntityDTO);
    }

    /**
     * {@code DELETE  /akip-entities/:id} : delete the "id" akipEntity.
     *
     * @param id the id of the akipEntityDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akip-entities/{id}")
    public ResponseEntity<Void> deleteAkipEntity(@PathVariable Long id) {
        log.debug("REST request to delete AkipEntity : {}", id);
        akipEntityService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
