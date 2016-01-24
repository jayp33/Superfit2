package com.japhdroid.superfit2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by User on 24.01.2016.
 */
public class DateTimeParser {

    static Date getDateFromString(String dateTimeString) {
        Date dateFromString = null;
        dateTimeString = dateTimeString.replace("T", " ");
        dateTimeString = dateTimeString.replace("Z", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateFromString = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return dateFromString;
    }
}
