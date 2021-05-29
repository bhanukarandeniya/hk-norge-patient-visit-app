package com.norge.patientvisit.util;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Date;

public class DateTimeUtility {

    public static Date setTimeToZero(Date date) {
        return DateUtils.setMilliseconds(DateUtils.setSeconds(
                DateUtils.setMinutes(DateUtils.setHours(date, 0), 0), 0), 0);
    }

}
