package com.norge.patientvisit.dto;

import com.norge.patientvisit.domain.Holiday;

import java.io.Serializable;
import java.util.List;

public class _HolidayPageDto implements Serializable {

    private List<Holiday> list;

    public List<Holiday> getList() {
        return list;
    }

    public _HolidayPageDto setList(List<Holiday> list) {
        this.list = list;
        return this;
    }
}
