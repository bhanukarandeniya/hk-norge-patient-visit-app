package com.norge.patientvisit.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Subselect;

/**
 * A Billing.
 */
@Entity
@Table(name = "billing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Subselect("")
public class Billing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "visit_id", nullable = false)
    private Integer visitId;

    @NotNull
    @Column(name = "patient_id")
    private String patientId;

    @NotNull
    @Column(name = "physician_id", nullable = false)
    private String physicianId;

    @NotNull
    @Column(name = "billed", nullable = false)
    private Date billed;

    @NotNull
    @Column(name = "active_record", nullable = false)
    private boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Billing id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getVisitId() {
        return this.visitId;
    }

    public Billing visitId(Integer visitId) {
        this.visitId = visitId;
        return this;
    }

    public void setVisitId(Integer visitId) {
        this.visitId = visitId;
    }

    public String getPatientId() {
        return this.patientId;
    }

    public Billing patientId(String patientId) {
        this.patientId = patientId;
        return this;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPhysicianId() {
        return this.physicianId;
    }

    public Billing physicianId(String physicianId) {
        this.physicianId = physicianId;
        return this;
    }

    public void setPhysicianId(String physicianId) {
        this.physicianId = physicianId;
    }

    public Date getBilled() {
        return this.billed;
    }

    public Billing billed(Date billed) {
        this.billed = billed;
        return this;
    }

    public void setBilled(Date billed) {
        this.billed = billed;
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
        if (!(o instanceof Billing)) {
            return false;
        }
        return id != null && id.equals(((Billing) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Billing{" +
                "id=" + getId() +
                ", visitId=" + getVisitId() +
                ", patientId='" + getPatientId() + "'" +
                ", physicianId='" + getPhysicianId() + "'" +
                ", billed='" + getBilled() + "'" +
                "}";
    }
}
