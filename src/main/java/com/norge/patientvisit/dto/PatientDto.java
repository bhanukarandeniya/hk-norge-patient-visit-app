package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.Date;

public class PatientDto implements Serializable {

    private Long id;

    private String patientId;

    private String name;

    private Date dob;

    private Integer gender;

    private Date created;

    private Date modified;

    private Integer createdBy;

}
