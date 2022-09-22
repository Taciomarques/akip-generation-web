package com.akipgenerationweb.web.rest;

import com.akipgenerationweb.repository.EntidadeRepository;
import com.akipgenerationweb.service.EntidadeService;
import com.akipgenerationweb.service.dto.EntidadeDTO;
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
 * REST controller for managing {@link com.akipgenerationweb.domain.Entidade}.
 */
@RestController
@RequestMapping("/api")
public class EntidadeResource {

    private final Logger log = LoggerFactory.getLogger(EntidadeResource.class);

    private static final String ENTITY_NAME = "entidade";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EntidadeService entidadeService;

    private final EntidadeRepository entidadeRepository;

    public EntidadeResource(EntidadeService entidadeService, EntidadeRepository entidadeRepository) {
        this.entidadeService = entidadeService;
        this.entidadeRepository = entidadeRepository;
    }

    /**
     * {@code POST  /entidades} : Create a new entidade.
     *
     * @param entidadeDTO the entidadeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new entidadeDTO, or with status {@code 400 (Bad Request)} if the entidade has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/entidades")
    public ResponseEntity<EntidadeDTO> createEntidade(@RequestBody EntidadeDTO entidadeDTO) throws URISyntaxException {
        log.debug("REST request to save Entidade : {}", entidadeDTO);
        if (entidadeDTO.getId() != null) {
            throw new BadRequestAlertException("A new entidade cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EntidadeDTO result = entidadeService.save(entidadeDTO);
        return ResponseEntity
            .created(new URI("/api/entidades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /entidades/:id} : Updates an existing entidade.
     *
     * @param id the id of the entidadeDTO to save.
     * @param entidadeDTO the entidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadeDTO,
     * or with status {@code 400 (Bad Request)} if the entidadeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the entidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/entidades/{id}")
    public ResponseEntity<EntidadeDTO> updateEntidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntidadeDTO entidadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to update Entidade : {}, {}", id, entidadeDTO);
        if (entidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        EntidadeDTO result = entidadeService.save(entidadeDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidadeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /entidades/:id} : Partial updates given fields of an existing entidade, field will ignore if it is null
     *
     * @param id the id of the entidadeDTO to save.
     * @param entidadeDTO the entidadeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated entidadeDTO,
     * or with status {@code 400 (Bad Request)} if the entidadeDTO is not valid,
     * or with status {@code 404 (Not Found)} if the entidadeDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the entidadeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/entidades/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<EntidadeDTO> partialUpdateEntidade(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EntidadeDTO entidadeDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update Entidade partially : {}, {}", id, entidadeDTO);
        if (entidadeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, entidadeDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!entidadeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<EntidadeDTO> result = entidadeService.partialUpdate(entidadeDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, entidadeDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /entidades} : get all the entidades.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of entidades in body.
     */
    @GetMapping("/entidades")
    public List<EntidadeDTO> getAllEntidades() {
        log.debug("REST request to get all Entidades");
        return entidadeService.findAll();
    }

    /**
     * {@code GET  /entidades/:id} : get the "id" entidade.
     *
     * @param id the id of the entidadeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the entidadeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/entidades/{id}")
    public ResponseEntity<EntidadeDTO> getEntidade(@PathVariable Long id) {
        log.debug("REST request to get Entidade : {}", id);
        Optional<EntidadeDTO> entidadeDTO = entidadeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(entidadeDTO);
    }

    /**
     * {@code DELETE  /entidades/:id} : delete the "id" entidade.
     *
     * @param id the id of the entidadeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/entidades/{id}")
    public ResponseEntity<Void> deleteEntidade(@PathVariable Long id) {
        log.debug("REST request to delete Entidade : {}", id);
        entidadeService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
