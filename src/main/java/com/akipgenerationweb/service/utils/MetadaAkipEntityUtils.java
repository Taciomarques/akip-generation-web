package com.akipgenerationweb.service.utils;

import com.akipgenerationweb.service.dto.AkipEntityDTO;
import com.akipgenerationweb.service.dto.AkipFieldDTO;
import com.akipgenerationweb.service.dto.AkipRelationshipDTO;
import com.akipgenerationweb.service.dto.AttachmentDTO;
import com.akipgenerationweb.service.dto.MetadataAkipEntityDTO;
import com.akipgenerationweb.service.dto.MetadataAkipFieldDTO;
import com.akipgenerationweb.service.dto.MetadataAkipRelationshipDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.util.DefaultIndenter;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;

public class MetadaAkipEntityUtils {

    private static ObjectMapper mapper = new ObjectMapper();
    private static final String JSON = ".json";

    private static final String ENTITY_TYPE_DOMAIN = "domain";
    private static final String ENTITY_TYPE_START_FORM = "start-form";
    private static final String ENTITY_TYPE_PROCESS_BINDING = "process-binding";
    private static final String ENTITY_TYPE_USER_TASK = "user-task-form";
    private static final String ENTITY_TYPE_SERVICE_TASK = "service-task";
    private static final String ENUM = "enum";

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
        return createMetadataAkipEntity(akipEntityStartForm, processBpmnId, domainEntityName, processEntityName, ENTITY_TYPE_START_FORM);
    }

    public static AttachmentDTO createMetadataAkipEntityProcessBinding(
        AkipEntityDTO akipEntityProcessBinding,
        String processBpmnId,
        String domainEntityName
    ) throws JsonProcessingException {
        return createMetadataAkipEntity(akipEntityProcessBinding, processBpmnId, domainEntityName, null, ENTITY_TYPE_PROCESS_BINDING);
    }

    public static List<AttachmentDTO> createMetadatasAkipEntitiesDomain(List<AkipEntityDTO> akipEntitiesDomain)
        throws JsonProcessingException {
        List<AttachmentDTO> metadatasAkipEntitiesDomain = new ArrayList<>();

        for (AkipEntityDTO akipEntityDomain : akipEntitiesDomain) {
            metadatasAkipEntitiesDomain.add(createMetadataAkipEntity(akipEntityDomain, null, null, null, ENTITY_TYPE_DOMAIN));
        }

        return metadatasAkipEntitiesDomain;
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
                createMetadataAkipEntity(akipEntityTask, processBpmnId, domainEntityName, processEntityName, entityType)
            );
        }

        return metadatasAkipEntitiesTask;
    }

    public static AttachmentDTO createMetadataAkipEntityDomain(AkipEntityDTO akipEntityDomain) throws JsonProcessingException {
        return createMetadataAkipEntity(akipEntityDomain, null, null, null, ENTITY_TYPE_DOMAIN);
    }

    public static AttachmentDTO createMetadataAkipEntity(
        AkipEntityDTO akipEntity,
        String processBpmnId,
        String domainEntityName,
        String processEntityName,
        String entityType
    ) throws JsonProcessingException {
        MetadataAkipEntityDTO metadataAkipEntityDTO = new MetadataAkipEntityDTO();
        metadataAkipEntityDTO.setName(akipEntity.getName());
        metadataAkipEntityDTO.setEntityType(entityType);

        List<MetadataAkipFieldDTO> metadatasAkipFieldDTO = new ArrayList<>();

        for (AkipFieldDTO akipField : akipEntity.getFields()) {
            MetadataAkipFieldDTO metadataAkipFieldDTO = new MetadataAkipFieldDTO();
            metadataAkipFieldDTO.setFieldName(akipField.getFieldName());
            if (akipField.getFieldType().equals(ENUM)) {
                metadataAkipFieldDTO.setFieldType(akipField.getFieldEnum());
            } else {
                metadataAkipFieldDTO.setFieldType(akipField.getFieldType());
            }
            metadataAkipFieldDTO.setFieldValues(akipField.getFieldValues());
            metadataAkipFieldDTO.setFieldReadOnly(akipField.getFieldReadOnly());
            metadataAkipFieldDTO.setFieldValidateRules(akipField.getFieldValidateRules());
            metadataAkipFieldDTO.setFieldValidateRulesMin(akipField.getFieldValidateRulesMin());
            metadataAkipFieldDTO.setFieldValidateRulesMax(akipField.getFieldValidateRulesMax());
            metadataAkipFieldDTO.setFieldValidateRulesMinlength(akipField.getFieldValidateRulesMinlength());
            metadataAkipFieldDTO.setFieldValidateRulesMaxlength(akipField.getFieldValidateRulesMaxlength());
            metadataAkipFieldDTO.setFieldValidateRulesPattern(akipField.getFieldValidateRulesPattern());
            metadataAkipFieldDTO.setFieldTypeBlobContent(akipField.getFieldTypeBlobContent());
            metadatasAkipFieldDTO.add(metadataAkipFieldDTO);
        }

        metadataAkipEntityDTO.setFields(metadatasAkipFieldDTO);

        List<MetadataAkipRelationshipDTO> metadatasAkipRelationshipDTO = new ArrayList<>();

        for (AkipRelationshipDTO akipRelationship : akipEntity.getRelationships()) {
            MetadataAkipRelationshipDTO metadataAkipRelationshipDTO = new MetadataAkipRelationshipDTO();
            metadataAkipRelationshipDTO.setRelationshipName(akipRelationship.getRelationshipName());
            metadataAkipRelationshipDTO.setRelationshipType(akipRelationship.getRelationshipType());
            metadataAkipRelationshipDTO.setRelationshipValidateRules(akipRelationship.getRelationshipValidateRules());
            metadataAkipRelationshipDTO.setOtherEntityRelationshipName(akipRelationship.getOtherEntityRelationshipName());
            metadataAkipRelationshipDTO.setOtherEntityName(akipRelationship.getOtherEntityName());
            metadataAkipRelationshipDTO.setOtherEntityField(akipRelationship.getOtherEntityField());
            metadatasAkipRelationshipDTO.add(metadataAkipRelationshipDTO);
        }
        metadataAkipEntityDTO.setRelationships(metadatasAkipRelationshipDTO);

        if (processBpmnId != null) {
            metadataAkipEntityDTO.setProcessBpmnId(processBpmnId);
        }
        if (processEntityName != null) {
            metadataAkipEntityDTO.setProcessEntityName(processEntityName);
        }
        if (domainEntityName != null) {
            metadataAkipEntityDTO.setDomainEntityName(domainEntityName);
        }

        AttachmentDTO metadataAkipEntityAttachment = new AttachmentDTO();
        metadataAkipEntityAttachment.setName(akipEntity.getName().concat(JSON));
        metadataAkipEntityAttachment.setSpecificationFile(mapper.writer(configureTabsInJson()).writeValueAsBytes(metadataAkipEntityDTO));
        metadataAkipEntityAttachment.setCreateDateTime(DateUtils.getLocalDateTimeBrt());

        return metadataAkipEntityAttachment;
    }
}
