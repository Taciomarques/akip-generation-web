package com.akipgenerationweb.process.generationProcess.taskConfigureServiceTaskProcess;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipEntityService;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import javax.transaction.Transactional;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskConfigureServiceTaskProcessService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final RuntimeService runtimeService;

    private final AkipEntityService akipEntityService;

    private final TaskConfigureServiceTaskProcessMapper taskConfigureServiceTaskProcessMapper;

    private final String SERVICE_TASK = "serviceTask";

    public TaskConfigureServiceTaskProcessService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        RuntimeService runtimeService,
        AkipEntityService akipEntityService,
        TaskConfigureServiceTaskProcessMapper taskConfigureServiceTaskProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.runtimeService = runtimeService;
        this.akipEntityService = akipEntityService;
        this.taskConfigureServiceTaskProcessMapper = taskConfigureServiceTaskProcessMapper;
    }

    public TaskConfigureServiceTaskProcessContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskConfigureServiceTaskProcessMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO = new TaskConfigureServiceTaskProcessContextDTO();
        taskConfigureServiceTaskProcessContextDTO.setTaskInstance(taskInstanceDTO);
        taskConfigureServiceTaskProcessContextDTO.setGenerationProcess(generationProcess);
        taskConfigureServiceTaskProcessContextDTO.setAkipEntityServiceTask(
            createAkipEntityServiceTask(
                generationProcess,
                ((AkipEntityDTO) runtimeService.getVariable(taskInstanceDTO.getExecutionId(), SERVICE_TASK)).getName()
            )
        );

        return taskConfigureServiceTaskProcessContextDTO;
    }

    private static AkipEntityDTO createAkipEntityServiceTask(GenerationProcessDTO generationProcess, String akipEntityServiceTaskName) {
        AkipEntityDTO akipEntityStartForm = new AkipEntityDTO();
        akipEntityStartForm.setName(akipEntityServiceTaskName);
        akipEntityStartForm.setType(TypeEntity.SERVICE_TASK);
        akipEntityStartForm.setApplication(generationProcess.getAkipProcess().getApplication());
        return akipEntityStartForm;
    }

    public TaskConfigureServiceTaskProcessContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO) {
        taskConfigureServiceTaskProcessContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(akipEntityService.save(taskConfigureServiceTaskProcessContextDTO.getAkipEntityServiceTask()));

        akipProcessService.save(taskConfigureServiceTaskProcessContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskConfigureServiceTaskProcessContextDTO taskConfigureServiceTaskProcessContextDTO) {
        save(taskConfigureServiceTaskProcessContextDTO);
        taskInstanceService.complete(
            taskConfigureServiceTaskProcessContextDTO.getTaskInstance(),
            taskConfigureServiceTaskProcessContextDTO.getGenerationProcess()
        );
    }
}
