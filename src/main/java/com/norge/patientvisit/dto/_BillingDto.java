package com.norge.patientvisit.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

public class _BillingDto implements Serializable {

    private Long id;

    private Integer visitId;

    private String patientId;

    private String physicianId;

    private Date billed;
}
