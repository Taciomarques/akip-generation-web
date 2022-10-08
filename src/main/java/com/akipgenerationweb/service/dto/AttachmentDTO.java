package com.akipgenerationweb.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.akipgenerationweb.domain.Attachment} entity.
 */
public class AttachmentDTO implements Serializable {

    private static final long serialVersionUID = 4179583465048963297L;

    private Long id;

    private String name;

    private String specificationFileContentType;

    private byte[] specificationFile;

    private Long processId;

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

    public String getSpecificationFileContentType() {
        return specificationFileContentType;
    }

    public void setSpecificationFileContentType(String specificationFileContentType) {
        this.specificationFileContentType = specificationFileContentType;
    }

    public byte[] getSpecificationFile() {
        return specificationFile;
    }

    public void setSpecificationFile(byte[] specificationFile) {
        this.specificationFile = specificationFile;
    }

    public Long getProcessId() {
        return processId;
    }

    public void setProcessId(Long processId) {
        this.processId = processId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AttachmentDTO anexoDTO = (AttachmentDTO) o;
        if (anexoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anexoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return ("AttachmentDTO{" + "id=" + getId() + ", name='" + getName() + "'" + "}");
    }
}
