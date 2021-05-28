package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.List;

public class _HolidayPageDto implements Serializable {

    private List<_HolidayDto> list;

    public List<_HolidayDto> getList() {
        return list;
    }

    public _HolidayPageDto setList(List<_HolidayDto> list) {
        this.list = list;
        return this;
    }
}
