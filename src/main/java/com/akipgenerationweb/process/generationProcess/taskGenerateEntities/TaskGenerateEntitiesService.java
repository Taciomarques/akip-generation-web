package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.AttachmentService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityDomainDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityProcessDTO;
import com.akipgenerationweb.service.utils.MetadaAkipEntityUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private final TaskGenerateEntitiesMapper taskGenerateEntitiesMapper;

    public TaskGenerateEntitiesService(
        TaskInstanceService taskInstanceService,
        AkipProcessService akipProcessService,
        TaskInstanceRepository taskInstanceRepository,
        GenerationProcessRepository generationProcessRepository,
        TaskInstanceMapper taskInstanceMapper,
        AttachmentService attachmentService,
        TaskGenerateEntitiesMapper taskGenerateEntitiesMapper
    ) {
        this.taskInstanceService = taskInstanceService;
        this.akipProcessService = akipProcessService;
        this.taskInstanceRepository = taskInstanceRepository;
        this.generationProcessRepository = generationProcessRepository;
        this.taskInstanceMapper = taskInstanceMapper;
        this.attachmentService = attachmentService;
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

    private static void setMetadatasInTaskGenerateEntitiesContext(
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

        taskGenerateEntitiesContextDTO.setMetadataAkipEntityDomain(MetadaAkipEntityUtils.createMetadataAkipEntityDomain(akipEntityDomain));
        taskGenerateEntitiesContextDTO.setMetadataAkipEntityProcessBinding(
            MetadaAkipEntityUtils.createMetadataAkipEntityProcessBinding(
                akipEntityProcessBinding,
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadataAkipEntityStartForm(
            MetadaAkipEntityUtils.createMetadataAkipEntityStartForm(
                generationProcess
                    .getAkipProcess()
                    .getEntities()
                    .stream()
                    .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.START_FORM))
                    .findAny()
                    .get(),
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName(),
                akipEntityProcessBinding.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadatasAkipEntitiesUserTasks(
            MetadaAkipEntityUtils.createMetadatasAkipEntitiesUserTasks(
                generationProcess
                    .getAkipProcess()
                    .getEntities()
                    .stream()
                    .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.USER_TASK))
                    .collect(Collectors.toList()),
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityDomain.getName(),
                akipEntityProcessBinding.getName()
            )
        );

        taskGenerateEntitiesContextDTO.setMetadatasAkipEntitiesServiceTasks(
            MetadaAkipEntityUtils.createMetadatasAkipEntitiesServiceTasks(
                generationProcess
                    .getAkipProcess()
                    .getEntities()
                    .stream()
                    .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.SERVICE_TASK))
                    .collect(Collectors.toList()),
                generationProcess.getAkipProcess().getProcessBpmnId(),
                akipEntityProcessBinding.getName()
            )
        );
    }

    public TaskGenerateEntitiesContextDTO claim(Long taskInstanceId) throws JsonProcessingException {
        taskInstanceService.claim(taskInstanceId);
        return loadContext(taskInstanceId);
    }

    public void save(TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO) {
        taskGenerateEntitiesContextDTO
            .getMetadataAkipEntityDomain()
            .setProcessId(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId());

        taskGenerateEntitiesContextDTO
            .getMetadataAkipEntityProcessBinding()
            .setProcessId(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId());

        taskGenerateEntitiesContextDTO
            .getMetadataAkipEntityStartForm()
            .setProcessId(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId());

        taskGenerateEntitiesContextDTO
            .getMetadatasAkipEntitiesUserTasks()
            .stream()
            .forEach(
                metadataAkipEntityUserTask -> {
                    metadataAkipEntityUserTask.setProcessId(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId());
                }
            );

        taskGenerateEntitiesContextDTO
            .getMetadatasAkipEntitiesServiceTasks()
            .stream()
            .forEach(
                metadataAkipEntityServiceTask -> {
                    metadataAkipEntityServiceTask.setProcessId(
                        taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess().getId()
                    );
                }
            );

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .add(attachmentService.save(taskGenerateEntitiesContextDTO.getMetadataAkipEntityDomain()));

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .add(attachmentService.save(taskGenerateEntitiesContextDTO.getMetadataAkipEntityProcessBinding()));

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .add(attachmentService.save(taskGenerateEntitiesContextDTO.getMetadataAkipEntityStartForm()));

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .addAll(saveMetadatasAkipEntities(taskGenerateEntitiesContextDTO.getMetadatasAkipEntitiesUserTasks()));

        taskGenerateEntitiesContextDTO
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .addAll(saveMetadatasAkipEntities(taskGenerateEntitiesContextDTO.getMetadatasAkipEntitiesServiceTasks()));

        akipProcessService.save(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess());
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
