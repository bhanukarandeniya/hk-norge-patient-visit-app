package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.Date;

public class VisitDto implements Serializable {

    private Long id;

    private Date visitDate;

    private String reason;

    private Date created;

    private Date modified;

    private Integer createdBy;

    private Integer modifiedBy;

    private Boolean active;

    private PatientDto patientId;

    private PhysicianDto physicianId;

}
