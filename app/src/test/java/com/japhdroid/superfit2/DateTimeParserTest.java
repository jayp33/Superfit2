package com.japhdroid.superfit2;

import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

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
}