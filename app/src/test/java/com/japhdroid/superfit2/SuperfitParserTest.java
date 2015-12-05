package com.japhdroid.superfit2;

import junit.framework.Assert;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
        SuperfitParser parser = new SuperfitParser("garbage");
    }

    @Test
    public void testValidUrls() throws Exception {
        // http://m.mysuperfit.com/kursplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/kursplaene/berlin-mitte
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte
        // /di /mi /do /fr /sa /so

        SuperfitParser parser;

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-mitte/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain/so");

        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/di");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/mi");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/do");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/fr");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/sa");
        parser = new SuperfitParser("http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte/so");
    }

    @Test
    public void testCourseList() throws Exception {
        // Test requires network connection
        SuperfitParser parser = new SuperfitParser("http://m.mysuperfit.com/kursplaene/berlin-friedrichshain/do");
        parser.LoadData();
        List<SuperfitCourse> courseList = parser.getCourseList();
        Assert.assertTrue(courseList.size() > 0);
        for (SuperfitCourse course : courseList) {
            Assert.assertTrue(course.getType().equals("Kurs") || course.getType().equals("Team"));
            Assert.assertEquals(5, course.getTime().length());
            Assert.assertTrue(course.getName().endsWith(".jpg"));
        }
    }
}