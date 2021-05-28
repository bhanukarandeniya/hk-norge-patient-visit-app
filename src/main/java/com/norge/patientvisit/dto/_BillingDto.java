package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.Date;

public class _BillingDto implements Serializable {

    private Long id;

    private Integer visitId;

    private String patientId;

    private String physicianId;

    private Date billed;
}
