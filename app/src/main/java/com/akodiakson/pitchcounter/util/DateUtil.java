package com.akodiakson.pitchcounter.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ace0808 on 4/25/2016.
 */
public class DateUtil {

    public static String getDisplayableDate(String stringDateIn){
        try {
            Date yyyyMMdd = new SimpleDateFormat("yyyyMMdd").parse(stringDateIn);
            String formatted = new SimpleDateFormat("EEEE M/d/yy").format(yyyyMMdd);
            return formatted;
        } catch (ParseException e) {
            e.printStackTrace();
           return stringDateIn;
        }
    }
}
