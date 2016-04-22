package com.japhdroid.superfit2;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    static Date getTimeFromString(String dateTimeString) {
        Date timeFromString = null;
        dateTimeString = dateTimeString.substring(dateTimeString.indexOf("T") + 1);
        dateTimeString = dateTimeString.substring(0, 5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            timeFromString = simpleDateFormat.parse(dateTimeString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return timeFromString;
    }

    static String getTimeStringFromDate(Date date) {
        return date.toString().substring(11, 16);
    }

    static String getTimeAndDayStringFromDateAsOfToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        int todayDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        calendar.setTime(date);
        int dateDayOfYear = calendar.get(Calendar.DAY_OF_YEAR);
        if (todayDayOfYear == dateDayOfYear)
            return getTimeStringFromDate(date);
        if (date.getTime() - now.getTime() > (7 * 24 * 60 * 60 * 1000))
            throw new IllegalArgumentException("Date is too far in the future");
        if (dateDayOfYear - todayDayOfYear == 1)
            return "Morgen " + getTimeStringFromDate(date);
        String day = calendar.getDisplayName(calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN);
        return day + " " + getTimeStringFromDate(date);
    }

    static String getDaysInTheFuture(Date date) {
        String result = "";
        int daysInTheFuture = getDaysInTheFutureCount(date);
        switch (daysInTheFuture) {
            case 0:
                break;
            case 1:
                result = "■";
                break;
            case 2:
                result = "■■";
                break;
            default:
                result = "+" + daysInTheFuture;
        }
        return result;
    }

    static int getDaysInTheFutureCount(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1 = sunday .. 7 = saturday
        cal.setTime(date);
        int dayOfSuppliedWeek = cal.get(Calendar.DAY_OF_WEEK);

        int daysInTheFuture = dayOfSuppliedWeek - dayOfWeek;
        if (daysInTheFuture < 0)
            daysInTheFuture += 7;

        return daysInTheFuture;
    }
}
