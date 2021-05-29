package com.norge.patientvisit.dto;

import java.io.Serializable;
import java.util.List;

public class HolidayPageDto implements Serializable {

    private List<HolidayDto> list;

    public List<HolidayDto> getList() {
        return list;
    }

    public HolidayPageDto setList(List<HolidayDto> list) {
        this.list = list;
        return this;
    }
}
