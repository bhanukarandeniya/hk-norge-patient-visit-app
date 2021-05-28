package com.norge.patientvisit.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * A Physician.
 */
@Entity
@Table(name = "physician", uniqueConstraints = @UniqueConstraint(columnNames = {"physician_id"}))
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Physician implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "physician_id", nullable = false, unique = true)
    private String physicianId;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @NotNull
    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @NotNull
    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @NotNull
    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;

    @NotNull
    @Column(name = "active_record", nullable = false)
    private boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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
        return getClass().hashCode();
    }

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
