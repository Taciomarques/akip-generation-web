package com.akipgenerationweb.service.dto;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akipgenerationweb.domain.Process} entity.
 */
public class ProcessDTO implements Serializable {

    private Long id;

    private String name;

    private Integer percentageExecuted;

    private StatusProcess status;

    private ApplicationDTO application;

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

    public ApplicationDTO getApplication() {
        return application;
    }

    public void setApplication(ApplicationDTO application) {
        this.application = application;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProcessDTO)) {
            return false;
        }

        ProcessDTO processDTO = (ProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, processDTO.id);
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
