package com.akipgenerationweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.*;
import org.akip.domain.ProcessInstance;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A GenerationProcess.
 */
@Entity
@Table(name = "generation_process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GenerationProcess implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "processDefinition" }, allowSetters = true)
    private ProcessInstance processInstance;

    @OneToOne
    @JoinColumn(name = "akip_process_id")
    private AkipProcess akipProcess;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "data")
    private String data;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GenerationProcess id(Long id) {
        this.id = id;
        return this;
    }

    public ProcessInstance getProcessInstance() {
        return this.processInstance;
    }

    public void setProcessInstance(ProcessInstance processInstance) {
        this.processInstance = processInstance;
    }

    public GenerationProcess processInstance(ProcessInstance processInstance) {
        this.setProcessInstance(processInstance);
        return this;
    }

    public AkipProcess getAkipProcess() {
        return this.akipProcess;
    }

    public void setAkipProcess(AkipProcess process) {
        this.akipProcess = process;
    }

    public GenerationProcess Process(AkipProcess process) {
        this.setAkipProcess(process);
        return this;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GenerationProcess)) {
            return false;
        }
        return id != null && id.equals(((GenerationProcess) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GenerationProcess{" +
            "id=" + getId() +
            "}";
    }
}
