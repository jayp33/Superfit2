package com.japhdroid.superfit2;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        List<Lesson> lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3}, studios, courses).getLessons();
        int courseCount = 0;
        int teamCount = 0;
        for (Lesson lesson : lessons) {
            if (lesson.getCourse().getFloor() == Course.Floor.KURS)
                courseCount++;
            else
                teamCount++;
        }
        assertEquals(148, lessons.size());
        assertEquals(69, courseCount); // 72 total, 3 conflicts
        assertEquals(79, teamCount);   // 79 total, 0 conflicts
    }

    @Test
    public void testLessonId_220_IsCorrect() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3}, studios, courses);
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

    @Test
    public void testGetLessonsForSingleStudio() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        assertEquals(302, lessons.getLessons().size());
        List<Lesson> _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(3)});
        assertEquals(148, _lessons.size());
        for (Lesson lesson : _lessons)
            assertEquals(3, lesson.getStudio().getId());
        _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(5)});
        assertEquals(154, _lessons.size());
        for (Lesson lesson : _lessons)
            assertEquals(5, lesson.getStudio().getId());
    }

    @Test
    public void testGetLessonsForMultipleStudios() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        assertEquals(302, lessons.getLessons().size());
        List<Lesson> _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(3), studios.getStudioById(5)});
        assertEquals(302, _lessons.size());
    }

    @Test
    public void testGetFutureLessonsForSingleStudio() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3}, studios, courses);
        assertEquals(148, lessons.getLessons().size());
        List<Lesson> _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(3)}, true);
        for (int i = 0; i < _lessons.size() - 1; i++) {
            boolean actual = false;
            if (_lessons.get(i + 1).getStarttimeExact().getTime() > _lessons.get(i).getStarttimeExact().getTime())
                actual = true;
            if (_lessons.get(i + 1).getStarttimeExact().getTime() == _lessons.get(i).getStarttimeExact().getTime())
                if (_lessons.get(i + 1).getCourse().getFloor() == Course.Floor.TEAMTRAINING)
                    actual = true;
            assertTrue(actual);
        }
    }
}