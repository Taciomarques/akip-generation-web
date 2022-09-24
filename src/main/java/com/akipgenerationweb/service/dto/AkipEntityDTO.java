package com.akipgenerationweb.service.dto;

import com.akipgenerationweb.domain.AkipEntity;
import com.akipgenerationweb.domain.enumeration.TypeEntity;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link AkipEntity} entity.
 */
public class AkipEntityDTO implements Serializable {

    private Long id;

    private String name;

    private String fields;

    private String relations;

    private TypeEntity type;

    private AkipApplicationDTO application;

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

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getRelations() {
        return relations;
    }

    public void setRelations(String relations) {
        this.relations = relations;
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
            ", relations='" + getRelations() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
