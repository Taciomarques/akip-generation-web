package com.akipgenerationweb.service.dto;

import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link AkipEntity} entity.
 */
public class AkipEntityDTO implements Serializable {

    private Long id;

    private String name;

    private List<AkipFieldDTO> fields;

    private List<AkipRelationshipDTO> relationships;

    private TypeEntity type;

    private AkipApplicationDTO application;

    private Set<AkipProcessDTO> processes = new HashSet<>();

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

    public TypeEntity getType() {
        return type;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public AkipApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(AkipApplicationDTO application) {
        this.application = application;
    }

    public Set<AkipProcessDTO> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<AkipProcessDTO> processes) {
        this.processes = processes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipEntityDTO)) {
            return false;
        }

        AkipEntityDTO akipEntityDTO = (AkipEntityDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, akipEntityDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipEntityDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fields='" + getFields() + "'" +
            ", relationships='" + getRelationships() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
