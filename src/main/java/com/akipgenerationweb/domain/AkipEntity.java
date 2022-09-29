package com.akipgenerationweb.domain;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A AkipEntity.
 */
@javax.persistence.Entity
@Table(name = "akip_entity")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class AkipEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "fields")
    private String fields;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "relationships")
    private String relationships;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEntity type;

    @ManyToOne
    @JoinColumn(name = "akip_application_id")
    @JsonIgnoreProperties(value = { "entities", "processes" }, allowSetters = true)
    private AkipApplication application;

    @ManyToMany(mappedBy = "entities")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "entities", "application" }, allowSetters = true)
    private Set<AkipProcess> processes = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AkipEntity id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public AkipEntity name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFields() {
        return this.fields;
    }

    public AkipEntity fields(String fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getRelationships() {
        return this.relationships;
    }

    public AkipEntity relations(String relations) {
        this.relationships = relations;
        return this;
    }

    public void setRelationships(String relations) {
        this.relationships = relations;
    }

    public TypeEntity getType() {
        return this.type;
    }

    public AkipEntity type(TypeEntity type) {
        this.type = type;
        return this;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public AkipApplication getApplication() {
        return this.application;
    }

    public AkipEntity application(AkipApplication application) {
        this.setApplication(application);
        return this;
    }

    public void setApplication(AkipApplication application) {
        this.application = application;
    }

    public Set<AkipProcess> getProcesses() {
        return processes;
    }

    public void setProcesses(Set<AkipProcess> processes) {
        this.processes = processes;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AkipEntity)) {
            return false;
        }
        return id != null && id.equals(((AkipEntity) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "AKipEntity{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fields='" + getFields() + "'" +
            ", relations='" + getRelationships() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
