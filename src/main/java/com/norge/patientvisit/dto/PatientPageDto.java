package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.List;

public class PatientPageDto implements Serializable {

    private List<PatientDto> list;


    public List<PatientDto> getList() {
        return list;
    }

    public PatientPageDto setList(List<PatientDto> list) {
        this.list = list;
        return this;
    }
}
