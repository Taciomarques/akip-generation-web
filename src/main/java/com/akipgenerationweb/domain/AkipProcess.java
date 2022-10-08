package com.akipgenerationweb.domain;

import com.akipgenerationweb.domain.enumeration.StatusProcess;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;

/**
 * A AkipProcess.
 */
@javax.persistence.Entity
@Table(name = "akip_process")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipProcess implements Serializable {

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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name = "akip_process_akip_entity",
        joinColumns = @JoinColumn(name = "akip_process_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "akip_entity_id", referencedColumnName = "id")
    )
    @JsonIgnoreProperties(value = { "process", "application" }, allowSetters = true)
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<AkipEntity> entities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "akip_application_id")
    @JsonIgnoreProperties(value = { "entities", "processes" }, allowSetters = true)
    private AkipApplication application;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "process")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "process" }, allowSetters = true)
    private List<Attachment> attachments;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AkipProcess id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AkipProcess name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPercentageExecuted() {
        return this.percentageExecuted;
    }

    public AkipProcess percentageExecuted(Integer percentageExecuted) {
        this.percentageExecuted = percentageExecuted;
        return this;
    }

    public void setPercentageExecuted(Integer percentageExecuted) {
        this.percentageExecuted = percentageExecuted;
    }

    public StatusProcess getStatus() {
        return this.status;
    }

    public AkipProcess status(StatusProcess status) {
        this.status = status;
        return this;
    }

    public void setStatus(StatusProcess status) {
        this.status = status;
    }

    public Set<AkipEntity> getEntities() {
        return this.entities;
    }

    public AkipProcess entities(Set<AkipEntity> entities) {
        this.setEntities(entities);
        return this;
    }

    public AkipProcess addEntities(AkipEntity entity) {
        this.entities.add(entity);
        return this;
    }

    public AkipProcess removeEntities(AkipEntity entity) {
        this.entities.remove(entity);
        return this;
    }

    public void setEntities(Set<AkipEntity> entities) {
        this.entities = entities;
    }

    public AkipApplication getApplication() {
        return this.application;
    }

    public AkipProcess application(AkipApplication application) {
        this.setApplication(application);
        return this;
    }

    public void setApplication(AkipApplication akipApplication) {
        this.application = akipApplication;
    }

    public List<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<Attachment> attachments) {
        this.attachments = attachments;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipProcess)) {
            return false;
        }
        return id != null && id.equals(((AkipProcess) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AkipProcess{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", percentageExecuted=" + getPercentageExecuted() +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
