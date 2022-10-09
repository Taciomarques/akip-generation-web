package com.akipgenerationweb.service.utils;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityDomainDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityProcessDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class MetadaAkipEntityUtils {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final String JSON = ".json";
    private static final String ENTITY_TYPE_START_FORM = "start-form";
    private static final String ENTITY_TYPE_PROCESS_BINDING = "process-binding";
    private static final String ENTITY_TYPE_USER_TASK = "user-task-form";
    private static final String ENTITY_TYPE_SERVICE_TASK = "service-task";

    public static AttachmentDTO createMetadataAkipEntityDomain(AkipEntityDTO akipEntityDomain) throws JsonProcessingException {
        MetadataAkipEntityDomainDTO metadataAkipEntityDomainDTO = new MetadataAkipEntityDomainDTO();
        metadataAkipEntityDomainDTO.setName(akipEntityDomain.getName());
        metadataAkipEntityDomainDTO.setFields(akipEntityDomain.getFields());
        metadataAkipEntityDomainDTO.setRelationships(akipEntityDomain.getRelationships());
        //        metadataAkipEntityDomainDTO.setReadOnly(akipEntityDomain.getReadOnly());
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

    public static AttachmentDTO createMetadataAkipEntityStartForm(
        AkipEntityDTO akipEntityStartForm,
        String processBpmnId,
        String domainEntityName,
        String processEntityName
    ) throws JsonProcessingException {
        return createMetadataAkipEntityProcess(
            akipEntityStartForm,
            processBpmnId,
            domainEntityName,
            processEntityName,
            ENTITY_TYPE_START_FORM
        );
    }

    public static AttachmentDTO createMetadataAkipEntityProcessBinding(
        AkipEntityDTO akipEntityProcessBinding,
        String processBpmnId,
        String domainEntityName
    ) throws JsonProcessingException {
        return createMetadataAkipEntityProcess(
            akipEntityProcessBinding,
            processBpmnId,
            domainEntityName,
            null,
            ENTITY_TYPE_PROCESS_BINDING
        );
    }

    public static List<AttachmentDTO> createMetadatasAkipEntitiesUserTasks(
        List<AkipEntityDTO> akipEntitiesTasks,
        String processBpmnId,
        String domainEntityName,
        String processEntityName
    ) throws JsonProcessingException {
        return createMetadatasAkipEntitiesTasks(
            akipEntitiesTasks,
            processBpmnId,
            domainEntityName,
            processEntityName,
            ENTITY_TYPE_USER_TASK
        );
    }

    public static List<AttachmentDTO> createMetadatasAkipEntitiesServiceTasks(
        List<AkipEntityDTO> akipEntitiesTasks,
        String processBpmnId,
        String domainEntityName
    ) throws JsonProcessingException {
        return createMetadatasAkipEntitiesTasks(akipEntitiesTasks, processBpmnId, domainEntityName, null, ENTITY_TYPE_SERVICE_TASK);
    }

    private static List<AttachmentDTO> createMetadatasAkipEntitiesTasks(
        List<AkipEntityDTO> akipEntitiesTasks,
        String processBpmnId,
        String domainEntityName,
        String processEntityName,
        String entityType
    ) throws JsonProcessingException {
        List<AttachmentDTO> metadatasAkipEntitiesTask = new ArrayList<>();

        for (AkipEntityDTO akipEntityTask : akipEntitiesTasks) {
            metadatasAkipEntitiesTask.add(
                createMetadataAkipEntityProcess(akipEntityTask, processBpmnId, domainEntityName, processEntityName, entityType)
            );
        }

        return metadatasAkipEntitiesTask;
    }

    public static AttachmentDTO createMetadataAkipEntityProcess(
        AkipEntityDTO akipEntity,
        String processBpmnId,
        String domainEntityName,
        String processEntityName,
        String entityType
    ) throws JsonProcessingException {
        MetadataAkipEntityProcessDTO metadataAkipEntityProcessDTO = new MetadataAkipEntityProcessDTO();
        metadataAkipEntityProcessDTO.setName(akipEntity.getName());
        metadataAkipEntityProcessDTO.setEntityType(entityType);
        metadataAkipEntityProcessDTO.setFields(akipEntity.getFields());
        metadataAkipEntityProcessDTO.setRelationships(akipEntity.getRelationships());
        metadataAkipEntityProcessDTO.setProcessBpmnId(processBpmnId);
        metadataAkipEntityProcessDTO.setProcessEntityName(processEntityName);
        metadataAkipEntityProcessDTO.setDomainEntityName(domainEntityName);

        AttachmentDTO metadataAkipEntityAttachment = new AttachmentDTO();
        metadataAkipEntityAttachment.setName(akipEntity.getName().concat(JSON));

        metadataAkipEntityAttachment.setSpecificationFile(
            mapper.writer(configureTabsInJson()).writeValueAsBytes(metadataAkipEntityProcessDTO)
        );

        return metadataAkipEntityAttachment;
    }
}
