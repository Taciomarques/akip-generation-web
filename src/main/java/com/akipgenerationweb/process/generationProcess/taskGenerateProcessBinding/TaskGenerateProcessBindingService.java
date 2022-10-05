package com.akipgenerationweb.process.generationProcess.taskGenerateProcessBinding;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipEntityService;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import java.util.List;
import javax.transaction.Transactional;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskGenerateProcessBindingService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskGenerateProcessBindingMapper taskGenerateProcessBindingMapper;

    private final AkipEntityService akipEntityService;

    public TaskGenerateProcessBindingService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskGenerateProcessBindingMapper taskGenerateProcessBindingMapper,
        AkipEntityService akipEntityService
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskGenerateProcessBindingMapper = taskGenerateProcessBindingMapper;
        this.akipEntityService = akipEntityService;
    }

    public TaskGenerateProcessBindingContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGenerateProcessBindingMapper::toGenerationProcessDTO)
            .orElseThrow();

        AkipEntityDTO akipEntityProcessBinding = new AkipEntityDTO();
        akipEntityProcessBinding.setType(TypeEntity.PROCESS_BINDING);
        akipEntityProcessBinding.setName(generationProcess.getAkipProcess().getName());
        akipEntityProcessBinding.setApplication(generationProcess.getAkipProcess().getApplication());

        List<AkipEntityDTO> akipEntitiesDomain = akipEntityService.findByApplicationIdAndTypeEntity(
            generationProcess.getAkipProcess().getApplication().getId(),
            TypeEntity.DOMAIN
        );

        TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContextDTO = new TaskGenerateProcessBindingContextDTO();
        taskGenerateProcessBindingContextDTO.setTaskInstance(taskInstanceDTO);
        taskGenerateProcessBindingContextDTO.setGenerationProcess(generationProcess);
        taskGenerateProcessBindingContextDTO.setAkipEntityProcessBinding(akipEntityProcessBinding);
        taskGenerateProcessBindingContextDTO.setAkipEntitiesDomain(akipEntitiesDomain);

        return taskGenerateProcessBindingContextDTO;
    }

    public TaskGenerateProcessBindingContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContextDTO) {
        taskGenerateProcessBindingContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(taskGenerateProcessBindingContextDTO.getAkipEntityProcessBinding());
        //        taskGenerateProcessBindingContextDTO.getGenerationProcess().getAkipProcess().setPercentageExecuted(50);
        //        taskGenerateProcessBindingContextDTO.getGenerationProcess().getAkipProcess().setStatus(StatusProcess.WAITING_GENERATE_START_FORM);

        akipProcessService.save(taskGenerateProcessBindingContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskGenerateProcessBindingContextDTO taskGenerateProcessBindingContextDTO) {
        save(taskGenerateProcessBindingContextDTO);
        taskInstanceService.complete(
            taskGenerateProcessBindingContextDTO.getTaskInstance(),
            taskGenerateProcessBindingContextDTO.getGenerationProcess()
        );
    }
}
