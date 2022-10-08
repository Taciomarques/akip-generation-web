package com.akipgenerationweb.process.generationProcess.taskConfigureProcessBinding;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipEntityService;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AkipFieldDTO;
import com.akipgenerationweb.service.dto.AkipRelationshipDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import java.util.HashSet;
import java.util.List;
import javax.transaction.Transactional;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskConfigureProcessBindingService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final TaskConfigureProcessBindingMapper taskConfigureProcessBindingMapper;

    private final AkipEntityService akipEntityService;

    public TaskConfigureProcessBindingService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        TaskConfigureProcessBindingMapper taskConfigureProcessBindingMapper,
        AkipEntityService akipEntityService
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.taskConfigureProcessBindingMapper = taskConfigureProcessBindingMapper;
        this.akipEntityService = akipEntityService;
    }

    public TaskConfigureProcessBindingContextDTO loadContext(Long taskInstanceId) {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskConfigureProcessBindingMapper::toGenerationProcessDTO)
            .orElseThrow();

        AkipEntityDTO akipEntityProcessBinding = new AkipEntityDTO();
        akipEntityProcessBinding.setType(TypeEntity.PROCESS_BINDING);
        akipEntityProcessBinding.setName(generationProcess.getAkipProcess().getName());
        akipEntityProcessBinding.setApplication(generationProcess.getAkipProcess().getApplication());

        List<AkipEntityDTO> akipEntitiesDomain = akipEntityService.findByApplicationIdAndTypeEntity(
            generationProcess.getAkipProcess().getApplication().getId(),
            TypeEntity.DOMAIN
        );

        TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContextDTO = new TaskConfigureProcessBindingContextDTO();
        taskConfigureProcessBindingContextDTO.setTaskInstance(taskInstanceDTO);
        taskConfigureProcessBindingContextDTO.setGenerationProcess(generationProcess);
        taskConfigureProcessBindingContextDTO.setAkipEntityProcessBinding(akipEntityProcessBinding);
        taskConfigureProcessBindingContextDTO.setAkipEntitiesDomain(akipEntitiesDomain);

        return taskConfigureProcessBindingContextDTO;
    }

    public TaskConfigureProcessBindingContextDTO claim(Long taskInstanceId) {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContextDTO) {
        resetAkipFieldsProperties(taskConfigureProcessBindingContextDTO.getAkipEntityProcessBinding().getFields());
        resetAkipRelationshipsProperties(taskConfigureProcessBindingContextDTO.getAkipEntityProcessBinding().getRelationships());

        taskConfigureProcessBindingContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .add(akipEntityService.save(taskConfigureProcessBindingContextDTO.getAkipEntityProcessBinding()));

        akipProcessService.save(taskConfigureProcessBindingContextDTO.getGenerationProcess().getAkipProcess());
    }

    private static void resetAkipFieldsProperties(List<AkipFieldDTO> akipFields) {
        for (AkipFieldDTO akipField : akipFields) {
            akipField.setFieldReadOnly(false);
            akipField.setFieldValidateRules(null);
            akipField.setFieldValidateRulesMin(null);
            akipField.setFieldValidateRulesMax(null);
            akipField.setFieldValidateRulesMinlength(null);
            akipField.setFieldValidateRulesMaxlength(null);
            akipField.setFieldValidateRulesPattern(null);
        }
    }

    private static void resetAkipRelationshipsProperties(List<AkipRelationshipDTO> akipRelationships) {
        for (AkipRelationshipDTO akipRelationship : akipRelationships) {
            akipRelationship.setRelationshipValidateRules(null);
        }
    }

    public void complete(TaskConfigureProcessBindingContextDTO taskConfigureProcessBindingContextDTO) {
        save(taskConfigureProcessBindingContextDTO);
        taskInstanceService.complete(
            taskConfigureProcessBindingContextDTO.getTaskInstance(),
            taskConfigureProcessBindingContextDTO.getGenerationProcess()
        );
    }
}
