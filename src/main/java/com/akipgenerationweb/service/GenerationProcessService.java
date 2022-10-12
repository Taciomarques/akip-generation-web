package com.akipgenerationweb.service;

import com.akipgenerationweb.domain.GenerationProcess;
import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.akipgenerationweb.repository.AkipProcessRepository;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.security.SecurityUtils;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.mapper.GenerationProcessMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.akip.camunda.CamundaConstants;
import org.akip.domain.ProcessInstance;
import org.akip.service.ProcessInstanceService;
import org.camunda.bpm.engine.RuntimeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link GenerationProcess}.
 */
@Service
@Transactional
public class GenerationProcessService {

    public static final String BPMN_PROCESS_DEFINITION_ID = "GenerationProcess";

    private final Logger log = LoggerFactory.getLogger(GenerationProcessService.class);

    private final ProcessInstanceService processInstanceService;

    private final AkipProcessRepository akipProcessRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final GenerationProcessMapper generationProcessMapper;

    private final RuntimeService runtimeService;

    private final UserService userService;

    public GenerationProcessService(
        ProcessInstanceService processInstanceService,
        AkipProcessRepository akipProcessRepository,
        GenerationProcessRepository generationProcessRepository,
        GenerationProcessMapper generationProcessMapper,
        RuntimeService runtimeService,
        UserService userService
    ) {
        this.processInstanceService = processInstanceService;
        this.akipProcessRepository = akipProcessRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.generationProcessMapper = generationProcessMapper;
        this.runtimeService = runtimeService;
        this.userService = userService;
    }

    /**
     * Save a generationProcess.
     *
     * @param generationProcessDTO the entity to save.
     * @return the persisted entity.
     */
    public GenerationProcessDTO create(GenerationProcessDTO generationProcessDTO) {
        log.debug("Request to save GenerationProcess : {}", generationProcessDTO);

        GenerationProcess generationProcess = generationProcessMapper.toEntity(generationProcessDTO);

        generationProcess.getAkipProcess().setStatus(StatusProcess.WAITING_CONFIGURE_DOMAIN_ENTITY);
        generationProcess.getAkipProcess().setPercentageExecuted(10);

        //Saving the domainEntity
        akipProcessRepository.save(generationProcess.getAkipProcess());

        //Creating the process instance in the Camunda and setting it in the process entity
        ProcessInstance processInstance = processInstanceService.create(
            BPMN_PROCESS_DEFINITION_ID,
            "Process#" + generationProcess.getAkipProcess().getId(),
            generationProcessMapper.toDto(generationProcess)
        );
        generationProcess.setProcessInstance(processInstance);

        //Saving the process entity
        GenerationProcessDTO generationProcessDTOSaved = generationProcessMapper.toDto(generationProcessRepository.save(generationProcess));

        updateProcessEntityInTheCamundaEngine(processInstance, generationProcessDTOSaved);

        return generationProcessMapper.toDto(generationProcess);
    }

    private void updateProcessEntityInTheCamundaEngine(ProcessInstance processInstance, Object processEntity) {
        runtimeService.setVariable(processInstance.getCamundaProcessInstanceId(), CamundaConstants.PROCESS_INSTANCE, processEntity);
    }

    /**
     * Get all the generationProcesss.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<GenerationProcessDTO> findAll() {
        log.debug("Request to get all GenerationProcesss");
        return generationProcessRepository
            .findAll()
            .stream()
            .filter(
                generationProcess ->
                    generationProcess
                        .getAkipProcess()
                        .getApplication()
                        .getOwner()
                        .getId()
                        .equals(userService.getUserWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin().get()).get().getId())
            )
            .map(generationProcessMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one generationProcess by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenerationProcessDTO> findOne(Long id) {
        log.debug("Request to get GenerationProcess : {}", id);
        return generationProcessRepository.findById(id).map(generationProcessMapper::toDto);
    }

    /**
     * Get one generationProcess by id.
     *
     * @param processInstanceId the id of the processInstance associated to the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<GenerationProcessDTO> findByProcessInstanceId(Long processInstanceId) {
        log.debug("Request to get GenerationProcess by  processInstanceId: {}", processInstanceId);
        return generationProcessRepository.findByProcessInstanceId(processInstanceId).map(generationProcessMapper::toDto);
    }
}
