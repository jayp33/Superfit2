package com.japhdroid.superfit2;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 24.01.2016.
 */
public class DateTimeParserTest {

    @Test
    public void testGetDateFromString() throws Exception {
        Date parsedDate = DateTimeParser.getDateFromString("2015-03-06T12:06:02Z");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2015, 2, 6, 12, 6, 2);
        assertEquals(cal.getTime(), parsedDate);
    }

    @Test
    public void testGetTimeFromString() throws Exception {
        Date parsedDate = DateTimeParser.getTimeFromString("2015-03-06T12:30:00Z");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(1970, 0, 1, 12, 30, 0);
        assertEquals(cal.getTime(), parsedDate);
    }

    @Test
    public void testGetTimeAndDayStringFromToday() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date();
        calendar.setTime(now);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = Integer.toString(hour);
        if (hourStr.length() == 1)
            hourStr = "0" + hourStr;
        String minuteStr = Integer.toString(minute);
        if (minuteStr.length() == 1)
            minuteStr = "0" + minuteStr;
        String expected = hourStr + ":" + minuteStr;
        assertEquals(expected, DateTimeParser.getTimeAndDayStringFromDateAsOfToday(now));
    }

    @Test
    public void testGetTimeAndDayStringFromTomorrow() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date(new Date().getTime() + (24 * 60 * 60 * 1000));
        calendar.setTimeInMillis(now.getTime());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = Integer.toString(hour);
        if (hourStr.length() == 1)
            hourStr = "0" + hourStr;
        String minuteStr = Integer.toString(minute);
        if (minuteStr.length() == 1)
            minuteStr = "0" + minuteStr;
        String expected = "Morgen " + hourStr + ":" + minuteStr;
        assertEquals(expected, DateTimeParser.getTimeAndDayStringFromDateAsOfToday(now));
    }

    @Test
    public void testGetTimeAndDayStringFromTheDayAfterTomorrow() throws Exception {
        Calendar calendar = Calendar.getInstance();
        Date now = new Date(new Date().getTime() + (2 * 24 * 60 * 60 * 1000));
        calendar.setTimeInMillis(now.getTime());
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        String hourStr = Integer.toString(hour);
        if (hourStr.length() == 1)
            hourStr = "0" + hourStr;
        String minuteStr = Integer.toString(minute);
        if (minuteStr.length() == 1)
            minuteStr = "0" + minuteStr;
        String day = calendar.getDisplayName(calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN);
        String expected = day + " " + hourStr + ":" + minuteStr;
        assertEquals(expected, DateTimeParser.getTimeAndDayStringFromDateAsOfToday(now));
    }
}