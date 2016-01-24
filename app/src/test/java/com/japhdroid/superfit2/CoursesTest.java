package com.japhdroid.superfit2;

import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 24.01.2016.
 */
public class CoursesTest {

    @Test
    public void testCourseCount() throws Exception {
        List<Course> courses = new Courses(TestDataProvider.courses).getCourses();
        assertEquals(38, courses.size());
    }

    @Test
    public void testCourseId_5_and_34_AreCorrect() throws Exception {
        Courses courses = new Courses(TestDataProvider.courses);
        Course course = courses.getCourseById(5);
        assertEquals("BODYPUMP", course.getTitle());
        assertEquals(Course.Floor.KURS, course.getFloor());
        assertEquals(50, course.getDuration());
        assertEquals("http://superfit.navillo.de/system/courses/images/000/000/005/medium/LM_Bodypump.png?1349984102",
                course.getImageUrl());
        // "image_updated_at":"2012-10-11T19:35:02Z"
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2012, 9, 11, 19, 35, 02);
        assertEquals(cal.getTime(), course.getImageUpdatedAt());

        course = courses.getCourseById(34);
        assertEquals("TRX", course.getTitle());
        assertEquals(Course.Floor.TEAMTRAINING, course.getFloor());
        assertEquals(30, course.getDuration());
        assertEquals("http://superfit.navillo.de/system/courses/images/000/000/034/medium/TRX.png?1386248074",
                course.getImageUrl());
        // "image_updated_at":"2013-12-05T12:54:35Z"
        cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(2013, 11, 05, 12, 54, 35);
        assertEquals(cal.getTime(), course.getImageUpdatedAt());
    }
}