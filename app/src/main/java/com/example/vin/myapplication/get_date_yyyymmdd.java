package com.example.vin.myapplication;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by vin on 18/01/2017.
 */

public class get_date_yyyymmdd {

    public class DateUtil {
        public  Date addDays(Date date, int days) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, days); //minus number would decrement the days
            return cal.getTime();
        }
    }
}
