package com.japhdroid.superfit2;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitParserTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void testInvalidUrlThrowsException() throws Exception {
        exception.expect(IllegalArgumentException.class);
        SuperfitParser parser = new SuperfitParser("garbage", 0, false);
    }

    @Test
    public void testValidUrls() throws Exception {
        // http://m.mysuperfit.com/kursplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/kursplaene/berlin-mitte
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte
        // /di /mi /do /fr /sa /so

        SuperfitParser parser;

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/di", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/mi", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/fr", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/sa", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/so", 0, false);

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/di", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/mi", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/do", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/fr", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/sa", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/so", 0, false);

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/di", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/mi", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/do", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/fr", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/sa", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/so", 0, false);

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/di", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/mi", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/do", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/fr", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/sa", 0, false);
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/so", 0, false);
    }

/*
    @Test
    public void testCourseList() throws Exception {
        // Test requires network connection
        SuperfitParser parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do", -1, true);
        List<SuperfitCourse> courseList = parser.getCourseList();
        Assert.assertTrue(courseList.size() > 0);
        for (SuperfitCourse course : courseList) {
            Assert.assertTrue(course.getType().equals("Kurs") || course.getType().equals("Team"));
            Assert.assertNotNull(course.getTime());
            boolean hasValidCourseName = false;
            for (String courseName : SuperFitCourseMapping.name) {
                if (course.getName().equalsIgnoreCase(courseName)) {
                    hasValidCourseName = true;
                    break;
                }
            }
            Assert.assertTrue(hasValidCourseName);
            Assert.assertTrue(course.getFilename().endsWith(".jpg"));
        }
    }
*/

    @Test
    public void testParseCurrentDate() throws Exception {
        // Testing private method: Date parseTime(String time, int addNoOfDays)
        SuperfitParser parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do", 1, false);
        Method method = SuperfitParser.class.getDeclaredMethod("parseTime", new Class[]{String.class, int.class});
        method.setAccessible(true);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Date actual = (Date) method.invoke(parser, "09:00", 0);
        cal.setTime(actual);
        Assert.assertEquals(year, cal.get(Calendar.YEAR));
        Assert.assertEquals(month, cal.get(Calendar.MONTH));
        Assert.assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, cal.get(Calendar.MINUTE));
    }

    @Test
    public void testParseFutureDate() throws Exception {
        // Testing private method: Date parseTime(String time, int addNoOfDays)
        int addNoOfDays = 1;
        SuperfitParser parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do", addNoOfDays, false);
        Method method = SuperfitParser.class.getDeclaredMethod("parseTime", new Class[]{String.class, int.class});
        method.setAccessible(true);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(cal.getTimeInMillis() + addNoOfDays * (24 * 3600 * 1000)));
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        Date actual = (Date) method.invoke(parser, "09:00", addNoOfDays);
        cal.setTime(actual);
        Assert.assertEquals(year, cal.get(Calendar.YEAR));
        Assert.assertEquals(month, cal.get(Calendar.MONTH));
        Assert.assertEquals(day, cal.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(9, cal.get(Calendar.HOUR_OF_DAY));
        Assert.assertEquals(0, cal.get(Calendar.MINUTE));
    }

    @Test
    public void testGetUrlForAllDays() throws Exception {
        // Testing private method: String GetUrlForDay(String urlForDay, int daysInTheFuture)
        String baseUrl = "http://m.mysuperfit.com/kursplaene/berlin-friedrichshain";
        Method method = SuperfitParser.class.getDeclaredMethod("GetUrlForDay", new Class[]{String.class, int.class});
        SuperfitParser parser = new SuperfitParser(baseUrl + "/di", 0, false);
        method.setAccessible(true);
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
        String[][] dateUrlPart = {{"/so", "", "/di", "/mi", "/do", "/fr", "/sa"},
                {"", "/di", "/mi", "/do", "/fr", "/sa", "/so"},
                {"/di", "/mi", "/do", "/fr", "/sa", "/so", ""},
                {"/mi", "/do", "/fr", "/sa", "/so", "", "/di"},
                {"/do", "/fr", "/sa", "/so", "", "/di", "/mi"},
                {"/fr", "/sa", "/so", "", "/di", "/mi", "/do"},
                {"/sa", "/so", "", "/di", "/mi", "/do", "/fr"}};
        for (int i = 0; i < 7; i++) {
            String newUrl = (String) method.invoke(parser, baseUrl, i);
            Assert.assertEquals(baseUrl + dateUrlPart[i][dayOfWeek - 1], newUrl);
        }
    }
}