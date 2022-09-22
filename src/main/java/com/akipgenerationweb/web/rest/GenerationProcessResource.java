package com.akipgenerationweb.web.rest;

import com.akipgenerationweb.service.GenerationProcessService;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.akipgenerationweb.domain.GenerationProcess}.
 */
@RestController
@RequestMapping("/api")
public class GenerationProcessResource {

    private final Logger log = LoggerFactory.getLogger(GenerationProcessResource.class);

    private static final String ENTITY_NAME = "generationProcess";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final GenerationProcessService generationProcessService;

    public GenerationProcessResource(GenerationProcessService generationProcessService) {
        this.generationProcessService = generationProcessService;
    }

    /**
     * {@code POST  /generation-processes} : Create a new generationProcess.
     *
     * @param generationProcessDTO the generationProcessDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new generationProcessDTO, or with status {@code 400 (Bad Request)} if the generationProcess has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/generation-processes")
    public ResponseEntity<GenerationProcessDTO> create(@RequestBody GenerationProcessDTO generationProcessDTO) throws URISyntaxException {
        log.debug("REST request to save GenerationProcess : {}", generationProcessDTO);
        if (generationProcessDTO.getId() != null) {
            throw new BadRequestAlertException("A new generationProcess cannot already have an ID", ENTITY_NAME, "idexists");
        }
        GenerationProcessDTO result = generationProcessService.create(generationProcessDTO);
        return ResponseEntity
            .created(new URI("/api/generation-processes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /generation-processes} : get all the generationProcesss.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of generationProcesss in body.
     */
    @GetMapping("/generation-processes")
    public List<GenerationProcessDTO> getAllGenerationProcesss() {
        log.debug("REST request to get all GenerationProcesss");
        return generationProcessService.findAll();
    }

    /**
     * {@code GET  /generation-processes/:id} : get the "id" generationProcess.
     *
     * @param processInstanceId the id of the generationProcessDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the generationProcessDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/generation-processes/{processInstanceId}")
    public ResponseEntity<GenerationProcessDTO> getByProcessInstanceId(@PathVariable Long processInstanceId) {
        log.debug("REST request to get GenerationProcess by processInstanceId : {}", processInstanceId);
        Optional<GenerationProcessDTO> generationProcessDTO = generationProcessService.findByProcessInstanceId(processInstanceId);
        return ResponseUtil.wrapOrNotFound(generationProcessDTO);
    }
}
