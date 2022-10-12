package com.akipgenerationweb.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataAkipEntityDTO implements Serializable {

    private String name;

    private List<MetadataAkipFieldDTO> fields;

    private List<MetadataAkipRelationshipDTO> relationships;

    private String processBpmnId;

    private String domainEntityName;

    private String processEntityName;

    private String entityType;
    private boolean readOnly = false;

    private String service = "serviceClass";

    private String dto = "mapstruct";

    private boolean jpaMetamodelFiltering = false;

    private String pagination = "no";

    private boolean skipFakeData = true;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MetadataAkipFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<MetadataAkipFieldDTO> fields) {
        this.fields = fields;
    }

    public List<MetadataAkipRelationshipDTO> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<MetadataAkipRelationshipDTO> relationships) {
        this.relationships = relationships;
    }

    public String getProcessBpmnId() {
        return processBpmnId;
    }

    public void setProcessBpmnId(String processBpmnId) {
        this.processBpmnId = processBpmnId;
    }

    public String getDomainEntityName() {
        return domainEntityName;
    }

    public void setDomainEntityName(String domainEntityName) {
        this.domainEntityName = domainEntityName;
    }

    public String getProcessEntityName() {
        return processEntityName;
    }

    public void setProcessEntityName(String processEntityName) {
        this.processEntityName = processEntityName;
    }

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getService() {
        return service;
    }

    public String getDto() {
        return dto;
    }

    public boolean isJpaMetamodelFiltering() {
        return jpaMetamodelFiltering;
    }

    public String getPagination() {
        return pagination;
    }

    public boolean isSkipFakeData() {
        return skipFakeData;
    }
}
