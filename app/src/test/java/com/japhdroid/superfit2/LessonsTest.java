package com.japhdroid.superfit2;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by User on 24.01.2016.
 */
public class LessonsTest {

    private Studios studios;
    private Courses courses;

    @Before
    public void setUp() throws Exception {
        studios = new Studios(TestDataProvider.studios);
        courses = new Courses(TestDataProvider.courses);
    }

    @Test
    public void testLessonCount() throws Exception {
        List<Lesson> lessons = new Lessons(TestDataProvider.lessons_studio_3, studios, courses).getLessons();
        assertEquals(151, lessons.size());
    }

    @Test
    public void testLessonId_220_IsCorrect() throws Exception {
        Lessons lessons = new Lessons(TestDataProvider.lessons_studio_3, studios, courses);
        Lesson lesson = lessons.getLessonById(220);
        assertEquals("SUPERFIT Friedrichshain", lesson.getStudio().getTitle());
        assertEquals("BODYPUMP", lesson.getCourse().getTitle());
        assertEquals(Lesson.Capacity.GREEN, lesson.getCapacity());
        assertEquals(Lesson.Weekday.FRIDAY, lesson.getWeekday());
        // "starttime":"2012-10-12T11:00:00Z"
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(1970, 0, 1, 11, 0, 0);
        assertEquals(cal.getTime(), lesson.getStarttime());
    }
}