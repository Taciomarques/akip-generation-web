package com.akipgenerationweb.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Application.
 */
@Entity
@Table(name = "application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Application implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "repository_name")
    private String repositoryName;

    @Column(name = "create_date")
    private LocalDate createDate;

    @Column(name = "properties")
    private String properties;

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "process", "application" }, allowSetters = true)
    private Set<Entidade> etities = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "etities", "application" }, allowSetters = true)
    private Set<Process> processes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Application id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Application name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepositoryName() {
        return this.repositoryName;
    }

    public Application repositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        return this;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public Application createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getProperties() {
        return this.properties;
    }

    public Application properties(String properties) {
        this.properties = properties;
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Set<Entidade> getEtities() {
        return this.etities;
    }

    public Application etities(Set<Entidade> entidades) {
        this.setEtities(entidades);
        return this;
    }

    public Application addEtities(Entidade entidade) {
        this.etities.add(entidade);
        entidade.setApplication(this);
        return this;
    }

    public Application removeEtities(Entidade entidade) {
        this.etities.remove(entidade);
        entidade.setApplication(null);
        return this;
    }

    public void setEtities(Set<Entidade> entidades) {
        if (this.etities != null) {
            this.etities.forEach(i -> i.setApplication(null));
        }
        if (entidades != null) {
            entidades.forEach(i -> i.setApplication(this));
        }
        this.etities = entidades;
    }

    public Set<Process> getProcesses() {
        return this.processes;
    }

    public Application processes(Set<Process> processes) {
        this.setProcesses(processes);
        return this;
    }

    public Application addProcesses(Process process) {
        this.processes.add(process);
        process.setApplication(this);
        return this;
    }

    public Application removeProcesses(Process process) {
        this.processes.remove(process);
        process.setApplication(null);
        return this;
    }

    public void setProcesses(Set<Process> processes) {
        if (this.processes != null) {
            this.processes.forEach(i -> i.setApplication(null));
        }
        if (processes != null) {
            processes.forEach(i -> i.setApplication(this));
        }
        this.processes = processes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Application)) {
            return false;
        }
        return id != null && id.equals(((Application) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Application{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", repositoryName='" + getRepositoryName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", properties='" + getProperties() + "'" +
            "}";
    }
}
