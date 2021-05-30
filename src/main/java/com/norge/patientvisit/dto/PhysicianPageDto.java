package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.List;

public class PhysicianPageDto implements Serializable {

    private List<PhysicianDto> list;


    public List<PhysicianDto> getList() {
        return list;
    }

    public PhysicianPageDto setList(List<PhysicianDto> list) {
        this.list = list;
        return this;
    }
}
