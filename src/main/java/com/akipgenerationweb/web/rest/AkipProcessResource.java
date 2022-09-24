package com.akipgenerationweb.web.rest;

import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.repository.AkipProcessRepository;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipProcessDTO;
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
 * REST controller for managing {@link AkipProcess}.
 */
@RestController
@RequestMapping("/api")
public class AkipProcessResource {

    private final Logger log = LoggerFactory.getLogger(AkipProcessResource.class);

    private static final String ENTITY_NAME = "akipProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkipProcessService akipProcessService;

    private final AkipProcessRepository akipProcessRepository;

    public AkipProcessResource(AkipProcessService akipProcessService, AkipProcessRepository akipProcessRepository) {
        this.akipProcessService = akipProcessService;
        this.akipProcessRepository = akipProcessRepository;
    }

    /**
     * {@code POST  /akip-processes} : Create a new akiProcess.
     *
     * @param akipProcessDTO the akipProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akipProcessDTO, or with status {@code 400 (Bad Request)} if the akipProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akip-processes")
    public ResponseEntity<AkipProcessDTO> createProcess(@RequestBody AkipProcessDTO akipProcessDTO) throws URISyntaxException {
        log.debug("REST request to save AkipProcess : {}", akipProcessDTO);
        if (akipProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new akipProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipProcessDTO result = akipProcessService.save(akipProcessDTO);
        return ResponseEntity
            .created(new URI("/api/akip-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akip-processes/:id} : Updates an existing akipProcess.
     *
     * @param id the id of the akipProcessDTO to save.
     * @param akipProcessDTO the akipProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipProcessDTO,
     * or with status {@code 400 (Bad Request)} if the akipProcessDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akipProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akip-processes/{id}")
    public ResponseEntity<AkipProcessDTO> updateProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipProcessDTO akipProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkipProcess : {}, {}", id, akipProcessDTO);
        if (akipProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipProcessDTO result = akipProcessService.save(akipProcessDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipProcessDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akip-processes/:id} : Partial updates given fields of an existing akipProcess, field will ignore if it is null
     *
     * @param id the id of the akipProcessDTO to save.
     * @param akipProcessDTO the akipProcessDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipProcessDTO,
     * or with status {@code 400 (Bad Request)} if the akipProcessDTO is not valid,
     * or with status {@code 404 (Not Found)} if the akipProcessDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the akipProcessDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/akip-processes/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AkipProcessDTO> partialUpdateProcess(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipProcessDTO akipProcessDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AkipProcess partially : {}, {}", id, akipProcessDTO);
        if (akipProcessDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipProcessDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipProcessRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkipProcessDTO> result = akipProcessService.partialUpdate(akipProcessDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipProcessDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /akip-processes} : get all the akipProcesses.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipProcesses in body.
     */
    @GetMapping("/akip-processes")
    public List<AkipProcessDTO> getAllProcesses() {
        log.debug("REST request to get all AkipProcesses");
        return akipProcessService.findAll();
    }

    /**
     * {@code GET  /akip-processes/:id} : get the "id" akipProcess.
     *
     * @param id the id of the akipProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akipProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akip-processes/{id}")
    public ResponseEntity<AkipProcessDTO> getProcess(@PathVariable Long id) {
        log.debug("REST request to get AkipProcess : {}", id);
        Optional<AkipProcessDTO> akipProcessDTO = akipProcessService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akipProcessDTO);
    }

    /**
     * {@code DELETE  /akip-processes/:id} : delete the "id" AkipProcess.
     *
     * @param id the id of the akipProcessDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akip-processes/{id}")
    public ResponseEntity<Void> deleteProcess(@PathVariable Long id) {
        log.debug("REST request to delete AkipProcess : {}", id);
        akipProcessService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
