package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.time.Date;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Physician.
 */
@Entity
@Table(name = "physician")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Physician implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "physician_id", nullable = false)
    private String physicianId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;

    @OneToMany(mappedBy = "physicianId")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = {"patientId", "physicianId"}, allowSetters = true)
    private Set<Visit> visits = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Physician id(Long id) {
        this.id = id;
        return this;
    }

    public String getPhysicianId() {
        return this.physicianId;
    }

    public Physician physicianId(String physicianId) {
        this.physicianId = physicianId;
        return this;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }

    public String getName() {
        return this.name;
    }

    public Physician name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreated() {
        return this.created;
    }

    public Physician created(Date created) {
        this.created = created;
        return this;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return this.modified;
    }

    public Physician modified(Date modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public Physician createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public Physician modifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Set<Visit> getVisits() {
        return this.visits;
    }

    public Physician visits(Set<Visit> visits) {
        this.setVisits(visits);
        return this;
    }

    public Physician addVisit(Visit visit) {
        this.visits.add(visit);
        visit.setPhysicianId(this);
        return this;
    }

    public Physician removeVisit(Visit visit) {
        this.visits.remove(visit);
        visit.setPhysicianId(null);
        return this;
    }

    public void setVisits(Set<Visit> visits) {
        if (this.visits != null) {
            this.visits.forEach(i -> i.setPhysicianId(null));
        }
        if (visits != null) {
            visits.forEach(i -> i.setPhysicianId(this));
        }
        this.visits = visits;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Physician)) {
            return false;
        }
        return id != null && id.equals(((Physician) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Physician{" +
                "id=" + getId() +
                ", physicianId='" + getPhysicianId() + "'" +
                ", name='" + getName() + "'" +
                ", created='" + getCreated() + "'" +
                ", modified='" + getModified() + "'" +
                ", createdBy=" + getCreatedBy() +
                ", modifiedBy=" + getModifiedBy() +
                "}";
    }
}
