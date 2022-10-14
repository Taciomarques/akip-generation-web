package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipEntityService;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.AttachmentService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityDTO;
import com.akipgenerationweb.service.utils.MetadaAkipEntityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.akip.repository.TaskInstanceRepository;
import org.akip.service.TaskInstanceService;
import org.akip.service.dto.TaskInstanceDTO;
import org.akip.service.mapper.TaskInstanceMapper;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class TaskGenerateEntitiesService {

    private final TaskInstanceService taskInstanceService;

    private final AkipProcessService akipProcessService;

    private final TaskInstanceRepository taskInstanceRepository;

    private final GenerationProcessRepository generationProcessRepository;

    private final TaskInstanceMapper taskInstanceMapper;

    private final AttachmentService attachmentService;

    private final AkipEntityService akipEntityService;

    private final TaskGenerateEntitiesMapper taskGenerateEntitiesMapper;

    public TaskGenerateEntitiesService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        AttachmentService attachmentService,
        AkipEntityService akipEntityService,
        TaskGenerateEntitiesMapper taskGenerateEntitiesMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.attachmentService = attachmentService;
        this.akipEntityService = akipEntityService;
        this.taskGenerateEntitiesMapper = taskGenerateEntitiesMapper;
    }

    public TaskGenerateEntitiesContextDTO loadContext(Long taskInstanceId) throws JsonProcessingException {
        TaskInstanceDTO taskInstanceDTO = taskInstanceRepository
            .findById(taskInstanceId)
            .map(taskInstanceMapper::toDTOLoadTaskContext)
            .orElseThrow();

        GenerationProcessDTO generationProcess = generationProcessRepository
            .findByProcessInstanceId(taskInstanceDTO.getProcessInstance().getId())
            .map(taskGenerateEntitiesMapper::toGenerationProcessDTO)
            .orElseThrow();

        TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO = new TaskGenerateEntitiesContextDTO();
        taskGenerateEntitiesContextDTO.setTaskInstance(taskInstanceDTO);
        taskGenerateEntitiesContextDTO.setGenerationProcess(generationProcess);

        setMetadatasInTaskGenerateEntitiesContext(generationProcess, taskGenerateEntitiesContextDTO);

        return taskGenerateEntitiesContextDTO;
    }

    private void setMetadatasInTaskGenerateEntitiesContext(
        GenerationProcessDTO generationProcess,
        TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO
    ) throws JsonProcessingException {
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

        AkipEntityDTO akipEntityStartForm = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.START_FORM))
            .findAny()
            .get();

        List<AkipEntityDTO> akipEntitiesDomainNotGenerated = akipEntityService
            .findByApplicationIdAndTypeEntity(generationProcess.getAkipProcess().getApplication().getId(), TypeEntity.DOMAIN)
            .stream()
            .filter(akipEntityDTO -> !akipEntityDTO.isGenerated())
            .collect(Collectors.toList());

        List<AkipEntityDTO> akipEntitiesUserTask = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.USER_TASK))
            .collect(Collectors.toList());

        List<AkipEntityDTO> akipEntitiesServiceTask = generationProcess
            .getAkipProcess()
            .getEntities()
            .stream()
            .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.SERVICE_TASK))
            .collect(Collectors.toList());

        taskGenerateEntitiesContextDTO.setMetadatasAkipEntitiesDomain(
            MetadaAkipEntityUtils.createMetadatasAkipEntitiesDomain(akipEntitiesDomainNotGenerated)
        );
        taskGenerateEntitiesContextDTO.setMetadataAkipEntityProcessBinding(
            MetadaAkipEntityUtils.createMetadataAkipEntityProcessBinding(
                akipEntityProcessBinding,
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadataAkipEntityStartForm(
            MetadaAkipEntityUtils.createMetadataAkipEntityStartForm(
                akipEntityStartForm,
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName(),
                akipEntityProcessBinding.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadatasAkipEntitiesUserTasks(
            MetadaAkipEntityUtils.createMetadatasAkipEntitiesUserTasks(
                akipEntitiesUserTask,
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName(),
                akipEntityProcessBinding.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadatasAkipEntitiesServiceTasks(
            MetadaAkipEntityUtils.createMetadatasAkipEntitiesServiceTasks(
                akipEntitiesServiceTask,
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityProcessBinding.getName()
            )
        );
    }

    private void updateAkipEntitiesExecuted(List<AkipEntityDTO> akipEntities) {
        for (AkipEntityDTO akipEntity : akipEntities) {
            akipEntity.setGenerated(true);
            akipEntityService.save(akipEntity);
        }
    }

    public TaskGenerateEntitiesContextDTO claim(Long taskInstanceId) throws JsonProcessingException {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO) {
        List<AttachmentDTO> attachmentsMetadatasAkipEntities = new ArrayList<>();

        attachmentsMetadatasAkipEntities.addAll(taskGenerateEntitiesContextDTO.getMetadatasAkipEntitiesDomain());

        attachmentsMetadatasAkipEntities.add(taskGenerateEntitiesContextDTO.getMetadataAkipEntityProcessBinding());

        attachmentsMetadatasAkipEntities.add(taskGenerateEntitiesContextDTO.getMetadataAkipEntityStartForm());

        attachmentsMetadatasAkipEntities.addAll(taskGenerateEntitiesContextDTO.getMetadatasAkipEntitiesUserTasks());

        attachmentsMetadatasAkipEntities.addAll(taskGenerateEntitiesContextDTO.getMetadatasAkipEntitiesServiceTasks());

        attachmentsMetadatasAkipEntities =
            setProcessIdInMetadatas(
                attachmentsMetadatasAkipEntities,
                taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId()
            );

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .addAll(saveMetadatasAkipEntities(attachmentsMetadatasAkipEntities));

        List<AkipEntityDTO> entitiesNotExecuted = taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getEntities()
            .stream()
            .collect(Collectors.toList());
        entitiesNotExecuted.addAll(
            akipEntityService
                .findByApplicationIdAndTypeEntity(
                    taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getApplication().getId(),
                    TypeEntity.DOMAIN
                )
                .stream()
                .filter(akipEntityDTO -> !akipEntityDTO.isGenerated())
                .collect(Collectors.toList())
        );
        updateAkipEntitiesExecuted(
            taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getEntities().stream().collect(Collectors.toList())
        );

        akipProcessService.save(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess());
    }

    private List<AttachmentDTO> setProcessIdInMetadatas(List<AttachmentDTO> attachmentsMetadatasAkipEntities, Long akipProcessId) {
        attachmentsMetadatasAkipEntities
            .stream()
            .forEach(
                metadataAkipEntity -> {
                    metadataAkipEntity.setProcessId(akipProcessId);
                }
            );

        return attachmentsMetadatasAkipEntities;
    }

    public List<AttachmentDTO> saveMetadatasAkipEntities(List<AttachmentDTO> metadatasAkipEntities) {
        List<AttachmentDTO> metadatasAkipEntitiesSaved = new ArrayList<>();
        for (AttachmentDTO metadataAkipEntity : metadatasAkipEntities) {
            metadatasAkipEntitiesSaved.add(attachmentService.save(metadataAkipEntity));
        }
        return metadatasAkipEntitiesSaved;
    }

    public void complete(TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO) {
        save(taskGenerateEntitiesContextDTO);
        taskInstanceService.complete(
            taskGenerateEntitiesContextDTO.getTaskInstance(),
            taskGenerateEntitiesContextDTO.getGenerationProcess()
        );
    }
}
