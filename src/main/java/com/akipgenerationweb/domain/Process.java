package com.akipgenerationweb.domain;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Process.
 */
@Entity
@Table(name = "process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Process implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "percentage_executed")
    private Integer percentageExecuted;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusProcess status;

    @OneToMany(mappedBy = "process")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "process", "application" }, allowSetters = true)
    private Set<Entidade> etities = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "etities", "processes" }, allowSetters = true)
    private Application application;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Process id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Process name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentageExecuted() {
        return this.percentageExecuted;
    }

    public Process percentageExecuted(Integer percentageExecuted) {
        this.percentageExecuted = percentageExecuted;
        return this;
    }

    public void setPercentageExecuted(Integer percentageExecuted) {
        this.percentageExecuted = percentageExecuted;
    }

    public StatusProcess getStatus() {
        return this.status;
    }

    public Process status(StatusProcess status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProcess status) {
        this.status = status;
    }

    public Set<Entidade> getEtities() {
        return this.etities;
    }

    public Process etities(Set<Entidade> entidades) {
        this.setEtities(entidades);
        return this;
    }

    public Process addEtities(Entidade entidade) {
        this.etities.add(entidade);
        entidade.setProcess(this);
        return this;
    }

    public Process removeEtities(Entidade entidade) {
        this.etities.remove(entidade);
        entidade.setProcess(null);
        return this;
    }

    public void setEtities(Set<Entidade> entidades) {
        if (this.etities != null) {
            this.etities.forEach(i -> i.setProcess(null));
        }
        if (entidades != null) {
            entidades.forEach(i -> i.setProcess(this));
        }
        this.etities = entidades;
    }

    public Application getApplication() {
        return this.application;
    }

    public Process application(Application application) {
        this.setApplication(application);
        return this;
    }

    public void setApplication(Application application) {
        this.application = application;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Process)) {
            return false;
        }
        return id != null && id.equals(((Process) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Process{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", percentageExecuted=" + getPercentageExecuted() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
