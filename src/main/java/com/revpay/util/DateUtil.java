package com.revpay.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    private DateUtil() {}

    public static Date addMinutes(Date date, int minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, minutes);
        return cal.getTime();
    }

    public static boolean isExpired(Date expiryDate) {
        return expiryDate != null && expiryDate.before(new Date());
    }
}
