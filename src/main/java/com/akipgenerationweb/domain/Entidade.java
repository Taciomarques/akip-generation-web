package com.akipgenerationweb.domain;

import com.akipgenerationweb.domain.enumeration.TypeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Entidade.
 */
@Entity
@Table(name = "entidade")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Entidade implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "fields")
    private String fields;

    @Column(name = "relations")
    private String relations;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private TypeEntity type;

    @ManyToOne
    @JsonIgnoreProperties(value = { "etities", "application" }, allowSetters = true)
    private Process process;

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

    public Entidade id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Entidade name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFields() {
        return this.fields;
    }

    public Entidade fields(String fields) {
        this.fields = fields;
        return this;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }

    public String getRelations() {
        return this.relations;
    }

    public Entidade relations(String relations) {
        this.relations = relations;
        return this;
    }

    public void setRelations(String relations) {
        this.relations = relations;
    }

    public TypeEntity getType() {
        return this.type;
    }

    public Entidade type(TypeEntity type) {
        this.type = type;
        return this;
    }

    public void setType(TypeEntity type) {
        this.type = type;
    }

    public Process getProcess() {
        return this.process;
    }

    public Entidade process(Process process) {
        this.setProcess(process);
        return this;
    }

    public void setProcess(Process process) {
        this.process = process;
    }

    public Application getApplication() {
        return this.application;
    }

    public Entidade application(Application application) {
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
        if (!(o instanceof Entidade)) {
            return false;
        }
        return id != null && id.equals(((Entidade) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Entidade{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", fields='" + getFields() + "'" +
            ", relations='" + getRelations() + "'" +
            ", type='" + getType() + "'" +
            "}";
    }
}
