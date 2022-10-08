package com.akipgenerationweb.process.generationProcess.taskConfigureDomainEntity;

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
public class TaskConfigureDomainEntityService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskConfigureDomainEntityMapper taskConfigureDomainEntityMapper;

    private final AkipEntityService akipEntityService;

    private final String REQUIRED_CONFIGURE_OTHER_AKIP_ENTITY_DOMAIN = "requiredConfigureOtherAkipEntityDomain";

    private final String SKIP = "SKIP";

    public TaskConfigureDomainEntityService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskConfigureDomainEntityMapper taskConfigureDomainEntityMapper,
        AkipEntityService akipEntityService
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskConfigureDomainEntityMapper = taskConfigureDomainEntityMapper;
        this.akipEntityService = akipEntityService;
    }

    public TaskConfigureDomainEntityContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskConfigureDomainEntityMapper::toGenerationProcessDTO)
            .orElseThrow();

        AkipEntityDTO akipEntityDomain = new AkipEntityDTO();
        akipEntityDomain.setType(TypeEntity.DOMAIN);
        akipEntityDomain.setApplication(generationProcess.getAkipProcess().getApplication());

        TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContextDTO = new TaskConfigureDomainEntityContextDTO();
        taskConfigureDomainEntityContextDTO.setTaskInstance(taskInstanceDTO);
        taskConfigureDomainEntityContextDTO.setGenerationProcess(generationProcess);
        taskConfigureDomainEntityContextDTO.setAkipEntityDomain(akipEntityDomain);

        return taskConfigureDomainEntityContextDTO;
    }

    public TaskConfigureDomainEntityContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContextDTO) {
        if (
            !taskConfigureDomainEntityContextDTO
                .getGenerationProcess()
                .getData()
                .get(REQUIRED_CONFIGURE_OTHER_AKIP_ENTITY_DOMAIN)
                .equals(SKIP)
        ) {
            akipEntityService.save(taskConfigureDomainEntityContextDTO.getAkipEntityDomain());
        }

        akipProcessService.save(taskConfigureDomainEntityContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskConfigureDomainEntityContextDTO taskConfigureDomainEntityContextDTO) {
        save(taskConfigureDomainEntityContextDTO);
        taskInstanceService.complete(
            taskConfigureDomainEntityContextDTO.getTaskInstance(),
            taskConfigureDomainEntityContextDTO.getGenerationProcess()
        );
    }
}
