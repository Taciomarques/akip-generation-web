package com.akipgenerationweb.service.dto;

import java.io.Serializable;

public class AkipRelationshipDTO implements Serializable {

    private String relationshipName;

    private String relationshipType;

    private String otherEntityName;

    private String otherEntityRelationshipName;

    private String otherEntityField;

    private String[] relationshipValidateRules;

    public String getRelationshipName() {
        return relationshipName;
    }

    public void setRelationshipName(String relationshipName) {
        this.relationshipName = relationshipName;
    }

    public String getRelationshipType() {
        return relationshipType;
    }

    public void setRelationshipType(String relationshipType) {
        this.relationshipType = relationshipType;
    }

    public String getOtherEntityName() {
        return otherEntityName;
    }

    public void setOtherEntityName(String otherEntityName) {
        this.otherEntityName = otherEntityName;
    }

    public String getOtherEntityRelationshipName() {
        return otherEntityRelationshipName;
    }

    public void setOtherEntityRelationshipName(String otherEntityRelationshipName) {
        this.otherEntityRelationshipName = otherEntityRelationshipName;
    }

    public String getOtherEntityField() {
        return otherEntityField;
    }

    public void setOtherEntityField(String otherEntityField) {
        this.otherEntityField = otherEntityField;
    }

    public String[] getRelationshipValidateRules() {
        return relationshipValidateRules;
    }

    public void setRelationshipValidateRules(String[] relationshipValidateRules) {
        this.relationshipValidateRules = relationshipValidateRules;
    }

    @Override
    public String toString() {
        return (
            "{" +
            "relationshipName='" +
            getRelationshipName() +
            "'" +
            ", relationshipType='" +
            getRelationshipType() +
            "'" +
            ", otherEntityName='" +
            getOtherEntityName() +
            "'" +
            ", otherEntityField='" +
            getOtherEntityField() +
            "'" +
            ", otherEntityRelationshipName='" +
            getOtherEntityRelationshipName() +
            "'" +
            ", relationshipValidateRules='" +
            getRelationshipValidateRules() +
            "'" +
            "}"
        );
    }
}
