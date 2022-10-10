package com.akipgenerationweb.service.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class MetadataAkipEntityProcessDTO implements Serializable {

    private String name;

    private List<AkipFieldDTO> fields;

    private List<AkipRelationshipDTO> relationships;

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

    public List<AkipFieldDTO> getFields() {
        return fields;
    }

    public void setFields(List<AkipFieldDTO> fields) {
        this.fields = fields;
    }

    public List<AkipRelationshipDTO> getRelationships() {
        return relationships;
    }

    public void setRelationships(List<AkipRelationshipDTO> relationships) {
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
