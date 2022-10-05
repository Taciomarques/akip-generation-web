package com.akipgenerationweb.process.generationProcess.taskGenerateDomainEntity;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
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
public class TaskGenerateDomainEntityService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskGenerateDomainEntityMapper taskGenerateDomainEntityMapper;

    private final AkipEntityService akipEntityService;

    public TaskGenerateDomainEntityService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskGenerateDomainEntityMapper taskGenerateDomainEntityMapper,
        AkipEntityService akipEntityService
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskGenerateDomainEntityMapper = taskGenerateDomainEntityMapper;
        this.akipEntityService = akipEntityService;
    }

    public TaskGenerateDomainEntityContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGenerateDomainEntityMapper::toGenerationProcessDTO)
            .orElseThrow();

        AkipEntityDTO akipEntityDomain = new AkipEntityDTO();
        akipEntityDomain.setType(TypeEntity.DOMAIN);
        akipEntityDomain.setApplication(generationProcess.getAkipProcess().getApplication());

        TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContextDTO = new TaskGenerateDomainEntityContextDTO();
        taskGenerateDomainEntityContextDTO.setTaskInstance(taskInstanceDTO);
        taskGenerateDomainEntityContextDTO.setGenerationProcess(generationProcess);
        taskGenerateDomainEntityContextDTO.setAkipEntityDomain(akipEntityDomain);

        return taskGenerateDomainEntityContextDTO;
    }

    public TaskGenerateDomainEntityContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContextDTO) {
        if (!taskGenerateDomainEntityContextDTO.getGenerationProcess().getData().get("requiredGenerateOtherDomainEntity").equals("SKIP")) {
            akipEntityService.save(taskGenerateDomainEntityContextDTO.getAkipEntityDomain());
        }

        akipProcessService.save(taskGenerateDomainEntityContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskGenerateDomainEntityContextDTO taskGenerateDomainEntityContextDTO) {
        save(taskGenerateDomainEntityContextDTO);
        taskInstanceService.complete(
            taskGenerateDomainEntityContextDTO.getTaskInstance(),
            taskGenerateDomainEntityContextDTO.getGenerationProcess()
        );
    }
}
