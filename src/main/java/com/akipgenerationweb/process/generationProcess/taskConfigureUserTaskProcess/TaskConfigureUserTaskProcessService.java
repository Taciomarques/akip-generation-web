package com.akipgenerationweb.process.generationProcess.taskConfigureUserTaskProcess;

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
public class TaskConfigureUserTaskProcessService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final RuntimeService runtimeService;

    private final AkipEntityService akipEntityService;

    private final TaskConfigureUserTaskProcessMapper taskConfigureUserTaskProcessMapper;

    private final String USER_TASK = "userTask";

    public TaskConfigureUserTaskProcessService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        RuntimeService runtimeService,
        AkipEntityService akipEntityService,
        TaskConfigureUserTaskProcessMapper taskConfigureUserTaskProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.runtimeService = runtimeService;
        this.akipEntityService = akipEntityService;
        this.taskConfigureUserTaskProcessMapper = taskConfigureUserTaskProcessMapper;
    }

    public TaskConfigureUserTaskProcessContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskConfigureUserTaskProcessMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO = new TaskConfigureUserTaskProcessContextDTO();
        taskConfigureUserTaskProcessContextDTO.setTaskInstance(taskInstanceDTO);
        taskConfigureUserTaskProcessContextDTO.setGenerationProcess(generationProcess);
        String[] taskInstanceName = taskInstanceDTO.getName().split("\\s+");
        if (
            generationProcess
                .getAkipProcess()
                .getEntities()
                .stream()
                .filter(akipEntityDTO -> akipEntityDTO.getName().contains(taskInstanceName[taskInstanceName.length - 1]))
                .findAny()
                .isPresent()
        ) {
            taskConfigureUserTaskProcessContextDTO.setAkipEntityUserTask(
                generationProcess
                    .getAkipProcess()
                    .getEntities()
                    .stream()
                    .filter(akipEntityDTO -> akipEntityDTO.getName().contains(taskInstanceName[taskInstanceName.length - 1]))
                    .findAny()
                    .get()
            );
        } else {
            taskConfigureUserTaskProcessContextDTO.setAkipEntityUserTask(
                createAkipEntityUserTask(
                    generationProcess,
                    ((AkipEntityDTO) runtimeService.getVariable(taskInstanceDTO.getExecutionId(), USER_TASK)).getName()
                )
            );
        }

        return taskConfigureUserTaskProcessContextDTO;
    }

    private static AkipEntityDTO createAkipEntityUserTask(GenerationProcessDTO generationProcess, String akipEntityUserTaskName) {
        AkipEntityDTO akipEntityStartForm = new AkipEntityDTO();
        akipEntityStartForm.setName(akipEntityUserTaskName);
        akipEntityStartForm.setType(TypeEntity.USER_TASK);
        akipEntityStartForm.setApplication(generationProcess.getAkipProcess().getApplication());
        return akipEntityStartForm;
    }

    public TaskConfigureUserTaskProcessContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO) {
        taskConfigureUserTaskProcessContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(akipEntityService.save(taskConfigureUserTaskProcessContextDTO.getAkipEntityUserTask()));

        akipProcessService.save(taskConfigureUserTaskProcessContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskConfigureUserTaskProcessContextDTO taskConfigureUserTaskProcessContextDTO) {
        save(taskConfigureUserTaskProcessContextDTO);
        taskInstanceService.complete(
            taskConfigureUserTaskProcessContextDTO.getTaskInstance(),
            taskConfigureUserTaskProcessContextDTO.getGenerationProcess()
        );
    }
}
