package com.akipgenerationweb.process.generationProcess.taskConfigureStartFormProcess;

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
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskConfigureStartFormProcessService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final AkipEntityService akipEntityService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskConfigureStartFormProcessMapper taskConfigureStartFormProcessMapper;

    private static final String START_FORM = "StartForm";

    public TaskConfigureStartFormProcessService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        AkipEntityService akipEntityService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskConfigureStartFormProcessMapper taskConfigureStartFormProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.akipEntityService = akipEntityService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskConfigureStartFormProcessMapper = taskConfigureStartFormProcessMapper;
    }

    public TaskConfigureStartFormProcessContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskConfigureStartFormProcessMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO = new TaskConfigureStartFormProcessContextDTO();
        taskConfigureStartFormProcessContextDTO.setTaskInstance(taskInstanceDTO);
        taskConfigureStartFormProcessContextDTO.setGenerationProcess(generationProcess);
        taskConfigureStartFormProcessContextDTO.setAkipEntityStartForm(createAkipEntityStartForm(generationProcess));

        return taskConfigureStartFormProcessContextDTO;
    }

    private static AkipEntityDTO createAkipEntityStartForm(GenerationProcessDTO generationProcess) {
        AkipEntityDTO akipEntityProcessBinding = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.PROCESS_BINDING))
            .findAny()
            .get();

        AkipEntityDTO akipEntityStartForm = new AkipEntityDTO();
        akipEntityStartForm.setName(akipEntityProcessBinding.getName().concat(START_FORM));
        akipEntityStartForm.setType(TypeEntity.START_FORM);
        akipEntityStartForm.setApplication(generationProcess.getAkipProcess().getApplication());
        return akipEntityStartForm;
    }

    public TaskConfigureStartFormProcessContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO) {
        taskConfigureStartFormProcessContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(akipEntityService.save(taskConfigureStartFormProcessContextDTO.getAkipEntityStartForm()));

        akipProcessService.save(taskConfigureStartFormProcessContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskConfigureStartFormProcessContextDTO taskConfigureStartFormProcessContextDTO) {
        save(taskConfigureStartFormProcessContextDTO);
        taskInstanceService.complete(
            taskConfigureStartFormProcessContextDTO.getTaskInstance(),
            taskConfigureStartFormProcessContextDTO.getGenerationProcess()
        );
    }
}
