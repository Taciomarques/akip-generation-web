package com.akipgenerationweb.service.dto;

import com.akipgenerationweb.domain.AkipProcess;
import com.akipgenerationweb.domain.enumeration.StatusProcess;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link AkipProcess} entity.
 */
public class AkipProcessDTO implements Serializable {

    private Long id;

    private String name;

    private Integer percentageExecuted;

    private StatusProcess status;

    private AkipApplicationDTO application;

    private AttachmentDTO bpmn;

    private Set<AkipEntityDTO> entities = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentageExecuted() {
        return percentageExecuted;
    }

    public void setPercentageExecuted(Integer percentageExecuted) {
        this.percentageExecuted = percentageExecuted;
    }

    public StatusProcess getStatus() {
        return status;
    }

    public void setStatus(StatusProcess status) {
        this.status = status;
    }

    public AkipApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(AkipApplicationDTO application) {
        this.application = application;
    }

    public Set<AkipEntityDTO> getEntities() {
        return entities;
    }

    public void setEntities(Set<AkipEntityDTO> entities) {
        this.entities = entities;
    }

    public AttachmentDTO getBpmn() {
        return bpmn;
    }

    public void setBpmn(AttachmentDTO bpmn) {
        this.bpmn = bpmn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipProcessDTO)) {
            return false;
        }

        AkipProcessDTO akipProcessDTO = (AkipProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, akipProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProcessDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", percentageExecuted=" + getPercentageExecuted() +
            ", status='" + getStatus() + "'" +
            ", application=" + getApplication() +
            "}";
    }
}
