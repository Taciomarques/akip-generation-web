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
 * A AkipApplication.
 */
@javax.persistence.Entity
@Table(name = "akip_application")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipApplication implements Serializable {

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
    private Set<AkipEntity> entities = new HashSet<>();

    @OneToMany(mappedBy = "application")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entities", "application" }, allowSetters = true)
    private Set<AkipProcess> processes = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AkipApplication id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AkipApplication name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepositoryName() {
        return this.repositoryName;
    }

    public AkipApplication repositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
        return this;
    }

    public void setRepositoryName(String repositoryName) {
        this.repositoryName = repositoryName;
    }

    public LocalDate getCreateDate() {
        return this.createDate;
    }

    public AkipApplication createDate(LocalDate createDate) {
        this.createDate = createDate;
        return this;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    public String getProperties() {
        return this.properties;
    }

    public AkipApplication properties(String properties) {
        this.properties = properties;
        return this;
    }

    public void setProperties(String properties) {
        this.properties = properties;
    }

    public Set<AkipEntity> getEntities() {
        return this.entities;
    }

    public AkipApplication entities(Set<AkipEntity> entities) {
        this.setEntities(entities);
        return this;
    }

    public AkipApplication addEntities(AkipEntity akipEntity) {
        this.entities.add(akipEntity);
        akipEntity.setApplication(this);
        return this;
    }

    public AkipApplication removeEntities(AkipEntity akipEntity) {
        this.entities.remove(akipEntity);
        akipEntity.setApplication(null);
        return this;
    }

    public void setEntities(Set<AkipEntity> entities) {
        if (this.entities != null) {
            this.entities.forEach(i -> i.setApplication(null));
        }
        if (entities != null) {
            entities.forEach(i -> i.setApplication(this));
        }
        this.entities = entities;
    }

    public Set<AkipProcess> getProcesses() {
        return this.processes;
    }

    public AkipApplication processes(Set<AkipProcess> processes) {
        this.setProcesses(processes);
        return this;
    }

    public AkipApplication addProcesses(AkipProcess process) {
        this.processes.add(process);
        process.setApplication(this);
        return this;
    }

    public AkipApplication removeProcesses(AkipProcess process) {
        this.processes.remove(process);
        process.setApplication(null);
        return this;
    }

    public void setProcesses(Set<AkipProcess> processes) {
        if (this.processes != null) {
            this.processes.forEach(i -> i.setApplication(null));
        }
        if (processes != null) {
            processes.forEach(i -> i.setApplication(this));
        }
        this.processes = processes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipApplication)) {
            return false;
        }
        return id != null && id.equals(((AkipApplication) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipApplication{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", repositoryName='" + getRepositoryName() + "'" +
            ", createDate='" + getCreateDate() + "'" +
            ", properties='" + getProperties() + "'" +
            "}";
    }
}
