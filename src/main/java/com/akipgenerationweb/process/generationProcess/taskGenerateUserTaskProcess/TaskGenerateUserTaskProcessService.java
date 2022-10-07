package com.akipgenerationweb.process.generationProcess.taskGenerateUserTaskProcess;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
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
public class TaskGenerateUserTaskProcessService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final RuntimeService runtimeService;

    private final TaskGenerateUserTaskProcessMapper taskGenerateUserTaskProcessMapper;

    private final String USER_TASK = "userTask";

    public TaskGenerateUserTaskProcessService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        RuntimeService runtimeService,
        TaskGenerateUserTaskProcessMapper taskGenerateUserTaskProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.runtimeService = runtimeService;
        this.taskGenerateUserTaskProcessMapper = taskGenerateUserTaskProcessMapper;
    }

    public TaskGenerateUserTaskProcessContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGenerateUserTaskProcessMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO = new TaskGenerateUserTaskProcessContextDTO();
        taskGenerateUserTaskProcessContextDTO.setTaskInstance(taskInstanceDTO);
        taskGenerateUserTaskProcessContextDTO.setGenerationProcess(generationProcess);
        taskGenerateUserTaskProcessContextDTO.setAkipEntityUserTask(
            createAkipEntityUserTask(
                generationProcess,
                ((AkipEntityDTO) runtimeService.getVariable(taskInstanceDTO.getExecutionId(), USER_TASK)).getName()
            )
        );

        return taskGenerateUserTaskProcessContextDTO;
    }

    private static AkipEntityDTO createAkipEntityUserTask(GenerationProcessDTO generationProcess, String akipEntityUserTaskName) {
        AkipEntityDTO akipEntityDomain = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.DOMAIN))
            .findAny()
            .get();

        AkipEntityDTO akipEntityStartForm = new AkipEntityDTO();
        akipEntityStartForm.setName(akipEntityUserTaskName);
        akipEntityStartForm.setType(TypeEntity.USER_TASK);
        akipEntityStartForm.setFields(akipEntityDomain.getFields());
        akipEntityStartForm.setRelationships(akipEntityDomain.getRelationships());
        akipEntityStartForm.setApplication(generationProcess.getAkipProcess().getApplication());
        return akipEntityStartForm;
    }

    public TaskGenerateUserTaskProcessContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO) {
        taskGenerateUserTaskProcessContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(taskGenerateUserTaskProcessContextDTO.getAkipEntityUserTask());

        akipProcessService.save(taskGenerateUserTaskProcessContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskGenerateUserTaskProcessContextDTO taskGenerateUserTaskProcessContextDTO) {
        save(taskGenerateUserTaskProcessContextDTO);
        taskInstanceService.complete(
            taskGenerateUserTaskProcessContextDTO.getTaskInstance(),
            taskGenerateUserTaskProcessContextDTO.getGenerationProcess()
        );
    }
}
