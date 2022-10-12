package com.akipgenerationweb.service.dto;

import com.akipgenerationweb.domain.AkipApplication;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A DTO for the {@link AkipApplication} entity.
 */
public class AkipApplicationDTO implements Serializable {

    private Long id;

    private String name;

    private String repositoryName;

    private LocalDate createDate;

    private String properties;

    private UserDTO owner;

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

    public String getRepositoryName() {
        return repositoryName;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getProperties() {
        return properties;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public UserDTO getOwner() {
        return owner;
    }

    public void setOwner(UserDTO owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipApplicationDTO)) {
            return false;
        }

        AkipApplicationDTO akipApplicationDTO = (AkipApplicationDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, akipApplicationDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipApplicationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", repositoryName='" + getRepositoryName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", properties='" + getProperties() + "'" +
            "}";
    }
}
