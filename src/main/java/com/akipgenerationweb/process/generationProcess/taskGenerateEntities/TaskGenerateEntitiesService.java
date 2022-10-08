package com.akipgenerationweb.process.generationProcess.taskGenerateEntities;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.akipgenerationweb.repository.GenerationProcessRepository;
import com.akipgenerationweb.service.AkipProcessService;
import com.akipgenerationweb.service.AttachmentService;
import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.GenerationProcessDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityDomainDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    private static final String JSON = ".json";

    private static ObjectMapper mapper = new ObjectMapper();

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
        taskGenerateEntitiesContextDTO.setMetadataAkipEntityDomain(
            createMetadataAkipEntityDomain(
                generationProcess
                    .getAkipProcess()
                    .getEntities()
                    .stream()
                    .filter(akipEntityDTO -> akipEntityDTO.getType().equals(TypeEntity.DOMAIN))
                    .findAny()
                    .get()
            )
        );

        return taskGenerateEntitiesContextDTO;
    }

    private static AttachmentDTO createMetadataAkipEntityDomain(AkipEntityDTO akipEntityDomain) throws JsonProcessingException {
        MetadataAkipEntityDomainDTO metadataAkipEntityDomainDTO = new MetadataAkipEntityDomainDTO();
        metadataAkipEntityDomainDTO.setName(akipEntityDomain.getName());
        metadataAkipEntityDomainDTO.setFields(akipEntityDomain.getFields());
        metadataAkipEntityDomainDTO.setRelationships(akipEntityDomain.getRelationships());

        AttachmentDTO metadataAkipEntityDomainAttachment = new AttachmentDTO();
        metadataAkipEntityDomainAttachment.setName(akipEntityDomain.getName().concat(JSON));

        metadataAkipEntityDomainAttachment.setSpecificationFile(
            mapper.writer(configureTabsInJson()).writeValueAsBytes(metadataAkipEntityDomainDTO)
        );

        return metadataAkipEntityDomainAttachment;
    }

    private static DefaultPrettyPrinter configureTabsInJson() {
        DefaultPrettyPrinter.Indenter indenter = new DefaultIndenter("    ", DefaultIndenter.SYS_LF);
        DefaultPrettyPrinter printer = new DefaultPrettyPrinter();
        printer.indentObjectsWith(indenter);
        printer.indentArraysWith(indenter);
        return printer;
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
            .getGenerationProcess()
            .getAkipProcess()
            .getAttachments()
            .add(attachmentService.save(taskGenerateEntitiesContextDTO.getMetadataAkipEntityDomain()));

        akipProcessService.save(taskGenerateEntitiesContextDTO.getGenerationProcess().getAkipProcess());
    }

    public void complete(TaskGenerateEntitiesContextDTO taskGenerateEntitiesContextDTO) {
        save(taskGenerateEntitiesContextDTO);
        taskInstanceService.complete(
            taskGenerateEntitiesContextDTO.getTaskInstance(),
            taskGenerateEntitiesContextDTO.getGenerationProcess()
        );
    }
}
