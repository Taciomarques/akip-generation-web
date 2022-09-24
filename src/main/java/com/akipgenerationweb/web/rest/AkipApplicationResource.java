package com.akipgenerationweb.web.rest;

import com.akipgenerationweb.domain.AkipApplication;
import com.akipgenerationweb.repository.AkipApplicationRepository;
import com.akipgenerationweb.service.AkipApplicationService;
import com.akipgenerationweb.service.dto.AkipApplicationDTO;
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
 * REST controller for managing {@link AkipApplication}.
 */
@RestController
@RequestMapping("/api")
public class AkipApplicationResource {

    private final Logger log = LoggerFactory.getLogger(AkipApplicationResource.class);

    private static final String ENTITY_NAME = "akipApplication";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AkipApplicationService akipApplicationService;

    private final AkipApplicationRepository akipApplicationRepository;

    public AkipApplicationResource(AkipApplicationService akipApplicationService, AkipApplicationRepository akipApplicationRepository) {
        this.akipApplicationService = akipApplicationService;
        this.akipApplicationRepository = akipApplicationRepository;
    }

    /**
     * {@code POST  /akip-applications} : Create a new akipApplication.
     *
     * @param akipApplicationDTO the akipApplicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new akipApplicationDTO, or with status {@code 400 (Bad Request)} if the akipApplication has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/akip-applications")
    public ResponseEntity<AkipApplicationDTO> createApplication(@RequestBody AkipApplicationDTO akipApplicationDTO)
        throws URISyntaxException {
        log.debug("REST request to save AkipApplication : {}", akipApplicationDTO);
        if (akipApplicationDTO.getId() != null) {
            throw new BadRequestAlertException("A new akipApplication cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AkipApplicationDTO result = akipApplicationService.save(akipApplicationDTO);
        return ResponseEntity
            .created(new URI("/api/applications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /akip-applications/:id} : Updates an existing akipApplication.
     *
     * @param id the id of the akipApplicationDTO to save.
     * @param akipApplicationDTO the akipApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the akipApplicationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the akipApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/akip-applications/{id}")
    public ResponseEntity<AkipApplicationDTO> updateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipApplicationDTO akipApplicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to update AkipApplication : {}, {}", id, akipApplicationDTO);
        if (akipApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipApplicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipApplicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        AkipApplicationDTO result = akipApplicationService.save(akipApplicationDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipApplicationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /akip-applications/:id} : Partial updates given fields of an existing akipApplication, field will ignore if it is null
     *
     * @param id the id of the akipApplicationDTO to save.
     * @param akipApplicationDTO the akipApplicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated akipApplicationDTO,
     * or with status {@code 400 (Bad Request)} if the akipApplicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the akipApplicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the akipApplicationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/akip-applications/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<AkipApplicationDTO> partialUpdateApplication(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AkipApplicationDTO akipApplicationDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update AkipApplication partially : {}, {}", id, akipApplicationDTO);
        if (akipApplicationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, akipApplicationDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!akipApplicationRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<AkipApplicationDTO> result = akipApplicationService.partialUpdate(akipApplicationDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, akipApplicationDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /akip-applications} : get all the akipApplications.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of akipApplications in body.
     */
    @GetMapping("/akip-applications")
    public List<AkipApplicationDTO> getAllApplications() {
        log.debug("REST request to get all AkipApplications");
        return akipApplicationService.findAll();
    }

    /**
     * {@code GET  /akip-applications/:id} : get the "id" akipApplication.
     *
     * @param id the id of the akipApplicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the akipApplicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/akip-applications/{id}")
    public ResponseEntity<AkipApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get AkipApplication : {}", id);
        Optional<AkipApplicationDTO> akipApplicationDTO = akipApplicationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(akipApplicationDTO);
    }

    /**
     * {@code DELETE  /akip-applications/:id} : delete the "id" akipApplication.
     *
     * @param id the id of the akipApplicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/akip-applications/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete AkipApplication : {}", id);
        akipApplicationService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
