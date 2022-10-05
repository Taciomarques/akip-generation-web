package com.akipgenerationweb.process.generationProcess.taskGenerateStartFormProcess;

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
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskGenerateStartFormProcessService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskGenerateStartFormProcessMapper taskGenerateStartFormProcessMapper;

    public TaskGenerateStartFormProcessService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskGenerateStartFormProcessMapper taskGenerateStartFormProcessMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskGenerateStartFormProcessMapper = taskGenerateStartFormProcessMapper;
    }

    public TaskGenerateStartFormProcessContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGenerateStartFormProcessMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO = new TaskGenerateStartFormProcessContextDTO();
        taskGenerateStartFormProcessContextDTO.setTaskInstance(taskInstanceDTO);
        taskGenerateStartFormProcessContextDTO.setGenerationProcess(generationProcess);
        taskGenerateStartFormProcessContextDTO.setAkipEntityStartForm(createAkipEntityStartForm(generationProcess));

        return taskGenerateStartFormProcessContextDTO;
    }

    private static AkipEntityDTO createAkipEntityStartForm(GenerationProcessDTO generationProcess) {
        AkipEntityDTO akipEntityDomain = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.DOMAIN))
            .findAny()
            .get();
        AkipEntityDTO akipEntityProcessBinding = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.PROCESS_BINDING))
            .findAny()
            .get();

        AkipEntityDTO akipEntityStartForm = new AkipEntityDTO();
        akipEntityStartForm.setName(akipEntityProcessBinding.getName().concat("StartForm"));
        akipEntityStartForm.setType(TypeEntity.START_FORM);
        akipEntityStartForm.setFields(akipEntityDomain.getFields());
        akipEntityStartForm.setRelationships(akipEntityDomain.getRelationships());
        akipEntityStartForm.setApplication(generationProcess.getAkipProcess().getApplication());
        return akipEntityStartForm;
    }

    public TaskGenerateStartFormProcessContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO) {
        taskGenerateStartFormProcessContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(taskGenerateStartFormProcessContextDTO.getAkipEntityStartForm());
        //        taskGenerateStartFormProcessContextDTO.getGenerationProcess().getAkipProcess().setPercentageExecuted(67);

        akipProcessService.save(taskGenerateStartFormProcessContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskGenerateStartFormProcessContextDTO taskGenerateStartFormProcessContextDTO) {
        save(taskGenerateStartFormProcessContextDTO);
        taskInstanceService.complete(
            taskGenerateStartFormProcessContextDTO.getTaskInstance(),
            taskGenerateStartFormProcessContextDTO.getGenerationProcess()
        );
    }
}
