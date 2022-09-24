package com.akipgenerationweb.service.dto;

import java.io.Serializable;
import java.util.Objects;
import org.akip.service.dto.ProcessInstanceDTO;

/**
 * A DTO for the {@link com.akipgenerationweb.domain.GenerationProcess} entity.
 */
public class GenerationProcessDTO implements Serializable {

    private Long id;

    private ProcessInstanceDTO processInstance;

    private AkipProcessDTO akipProcess;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProcessInstanceDTO getProcessInstance() {
        return processInstance;
    }

    public void setProcessInstance(ProcessInstanceDTO processInstance) {
        this.processInstance = processInstance;
    }

    public AkipProcessDTO getAkipProcess() {
        return akipProcess;
    }

    public void setAkipProcess(AkipProcessDTO akipProcess) {
        this.akipProcess = akipProcess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenerationProcessDTO)) {
            return false;
        }

        GenerationProcessDTO generationProcessDTO = (GenerationProcessDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, generationProcessDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenerationProcessDTO{" +
            "id=" + getId() +
            ", processInstance=" + getProcessInstance() +
            ", akipProcess=" + getAkipProcess() +
            "}";
    }
}
