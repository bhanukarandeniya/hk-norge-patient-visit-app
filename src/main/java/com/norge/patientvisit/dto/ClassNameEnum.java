package com.norge.patientvisit.dto;

public enum ClassNameEnum {

    BillingDto("BillingDto"), HolidayDto("HolidayDto"),
    PatientDto("PatientDto"), PhysicianDto("PhysicianDto"), VisitDto("VisitDto"),

    Billing("Billing"), Holiday("Holiday"),
    Patient("Patient"), Physician("Physician"), Visit("Visit");

    private final String value;

    ClassNameEnum(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}