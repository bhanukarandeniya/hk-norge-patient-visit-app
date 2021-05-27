package com.mycompany.myapp.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * A Holiday.
 */
@Entity
@Table(name = "holiday")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Holiday implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "visit_date_time", nullable = false)
    private Date visitDateTime;

    @Column(name = "reason", nullable = false)
    private String reason;

    @Column(name = "created", nullable = false)
    private Date created;

    @Column(name = "modified")
    private Date modified;

    @Column(name = "created_by", nullable = false)
    private Integer createdBy;

    @Column(name = "modified_by", nullable = false)
    private Integer modifiedBy;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Holiday id(Long id) {
        this.id = id;
        return this;
    }

    public Date getVisitDateTime() {
        return this.visitDateTime;
    }

    public Holiday visitDateTime(Date visitDateTime) {
        this.visitDateTime = visitDateTime;
        return this;
    }

    public void setVisitDateTime(Date visitDateTime) {
        this.visitDateTime = visitDateTime;
    }

    public String getReason() {
        return this.reason;
    }

    public Holiday reason(String reason) {
        this.reason = reason;
        return this;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreated() {
        return this.created;
    }

    public Holiday created(Date created) {
        this.created = created;
        return this;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getModified() {
        return this.modified;
    }

    public Holiday modified(Date modified) {
        this.modified = modified;
        return this;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }

    public Integer getCreatedBy() {
        return this.createdBy;
    }

    public Holiday createdBy(Integer createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getModifiedBy() {
        return this.modifiedBy;
    }

    public Holiday modifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
        return this;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Holiday)) {
            return false;
        }
        return id != null && id.equals(((Holiday) o).id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Holiday{" +
                "id=" + getId() +
                ", visitDateTime='" + getVisitDateTime() + "'" +
                ", reason='" + getReason() + "'" +
                ", created='" + getCreated() + "'" +
                ", modified='" + getModified() + "'" +
                ", createdBy=" + getCreatedBy() +
                ", modifiedBy=" + getModifiedBy() +
                "}";
    }
}
