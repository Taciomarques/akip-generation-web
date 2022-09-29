package com.akipgenerationweb.service.dto;

import java.io.Serializable;

public class AkipFieldDTO implements Serializable {

    private String fieldName;

    private String fieldType;

    private String fieldValues;

    private boolean fieldReadOnly;

    private String[] fieldValidateRules;

    private Integer fieldValidateRulesMin;

    private Integer fieldValidateRulesMax;

    private Integer fieldValidateRulesMinlength;

    private Integer fieldValidateRulesMaxlength;

    private String fieldValidateRulesPattern;

    private String fieldTypeBlobContent;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getFieldValues() {
        return fieldValues;
    }

    public void setFieldValues(String fieldValues) {
        this.fieldValues = fieldValues;
    }

    public boolean getFieldReadOnly() {
        return fieldReadOnly;
    }

    public void setFieldReadOnly(boolean fieldReadOnly) {
        this.fieldReadOnly = fieldReadOnly;
    }

    public String[] getFieldValidateRules() {
        return fieldValidateRules;
    }

    public void setFieldValidateRules(String[] fieldValidateRules) {
        this.fieldValidateRules = fieldValidateRules;
    }

    public Integer getFieldValidateRulesMin() {
        return fieldValidateRulesMin;
    }

    public void setFieldValidateRulesMin(Integer fieldValidateRulesMin) {
        this.fieldValidateRulesMin = fieldValidateRulesMin;
    }

    public Integer getFieldValidateRulesMax() {
        return fieldValidateRulesMax;
    }

    public void setFieldValidateRulesMax(Integer fieldValidateRulesMax) {
        this.fieldValidateRulesMax = fieldValidateRulesMax;
    }

    public Integer getFieldValidateRulesMinlength() {
        return fieldValidateRulesMinlength;
    }

    public void setFieldValidateRulesMinlength(Integer fieldValidateRulesMinlength) {
        this.fieldValidateRulesMinlength = fieldValidateRulesMinlength;
    }

    public Integer getFieldValidateRulesMaxlength() {
        return fieldValidateRulesMaxlength;
    }

    public void setFieldValidateRulesMaxlength(Integer fieldValidateRulesMaxlength) {
        this.fieldValidateRulesMaxlength = fieldValidateRulesMaxlength;
    }

    public String getFieldValidateRulesPattern() {
        return fieldValidateRulesPattern;
    }

    public void setFieldValidateRulesPattern(String fieldValidateRulesPattern) {
        this.fieldValidateRulesPattern = fieldValidateRulesPattern;
    }

    public String getFieldTypeBlobContent() {
        return fieldTypeBlobContent;
    }

    public void setFieldTypeBlobContent(String fieldTypeBlobContent) {
        this.fieldTypeBlobContent = fieldTypeBlobContent;
    }

    @Override
    public String toString() {
        return (
            "{" +
            "fieldName='" +
            getFieldName() +
            "'" +
            ", fieldType='" +
            getFieldType() +
            "'" +
            ", fieldValues='" +
            getFieldValues() +
            "'" +
            ", fieldReadOnly='" +
            getFieldReadOnly() +
            "'" +
            "}"
        );
    }
}
