package com.japhdroid.superfit2;

import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
        assertEquals(154, lessons.size());
        assertEquals(75, courseCount); // 78 total, 3 conflicts
        assertEquals(79, teamCount);   // 79 total, 0 conflicts
    }

    @Test
    public void testLessonId_220_IsCorrect() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3}, studios, courses);
        Lesson lesson = lessons.getLessonById(220);
        assertEquals("SUPERFIT Friedrichshain", lesson.getStudio().getTitle());
        assertEquals("BODYPUMP", lesson.getCourse().getTitle());
        assertFalse(lesson.getCourse().isEnglish());
        assertFalse(lesson.isEnglish());
        assertEquals(Lesson.Capacity.GREEN, lesson.getCapacity());
        assertEquals(Lesson.Weekday.FRIDAY, lesson.getWeekday());
        // "starttime":"2012-10-12T11:00:00Z"
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(1970, 0, 1, 11, 0, 0);
        assertEquals(cal.getTime(), lesson.getStarttime());
    }

    @Test
    public void testLessonId_513_IsCorrect() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_5}, studios, courses);
        Lesson lesson = lessons.getLessonById(513);
        assertEquals("SUPERFIT Mitte", lesson.getStudio().getTitle());
        assertEquals("Yoga", lesson.getCourse().getTitle());
        assertEquals(14, lesson.getCourse().getId()); // originally it is 54 "YOGA (english)"
        assertFalse(lesson.getCourse().isEnglish());
        assertTrue(lesson.isEnglish());
        assertEquals(Lesson.Capacity.YELLOW, lesson.getCapacity());
        assertEquals(Lesson.Weekday.TUESDAY, lesson.getWeekday());
        // "starttime":"2016-03-29T20:30:00Z"
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(0);
        cal.set(1970, 0, 1, 20, 30, 0);
        assertEquals(cal.getTime(), lesson.getStarttime());
    }

    @Test
    public void testGetLessonsForSingleStudio() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        assertEquals(309, lessons.getLessons().size());
        List<Lesson> _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(3)});
        assertEquals(154, _lessons.size());
        for (Lesson lesson : _lessons)
            assertEquals(3, lesson.getStudio().getId());
        _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(5)});
        assertEquals(155, _lessons.size());
        for (Lesson lesson : _lessons)
            assertEquals(5, lesson.getStudio().getId());
    }

    @Test
    public void testGetLessonsForMultipleStudios() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        assertEquals(309, lessons.getLessons().size());
        List<Lesson> _lessons = lessons.getLessons(new Studio[]{studios.getStudioById(3), studios.getStudioById(5)});
        assertEquals(309, _lessons.size());
    }

    @Test
    public void testGetFutureLessonsForSingleStudio() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3}, studios, courses);
        assertEquals(154, lessons.getLessons().size());
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

    @Test
    public void testLessonCollectionSorting() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        Map<Course, List<Lesson>> collections = lessons.getLessonCollections();
        for (int i = 0; i < collections.size(); i++) {
            Object key = collections.keySet().toArray()[i];
            List<Lesson> collection = collections.get(key);
            for (int j = 0; j < collection.size() - 1; j++) {
                boolean actual = false;
                if (collection.get(j + 1).getStarttimeExact().getTime() > collection.get(j).getStarttimeExact().getTime())
                    actual = true;
                if (collection.get(j + 1).getStarttimeExact().getTime() == collection.get(j).getStarttimeExact().getTime())
                    if (collection.get(j + 1).getStudio().getId() > collection.get(j).getStudio().getId())
                        actual = true;
                assertTrue(actual);
            }
        }
    }

    @Test
    public void testLessonCollectionsSortingByName() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        Map<Course, List<Lesson>> collections = lessons.getLessonCollections();
        List<Course> sortingByName = lessons.getSortingByName();
        for (int i = 0; i < sortingByName.size() - 1; i++) {
            Course course1 = sortingByName.get(i);
            Course course2 = sortingByName.get(i + 1);
            assertTrue(course1.compareTo(course2) < 0);
        }
    }

    @Test
    public void testLessonsGroupedSortingByStarttime() throws Exception {
        Lessons lessons = new Lessons(new String[]{TestDataProvider.lessons_studio_3,
                TestDataProvider.lessons_studio_5}, studios, courses);
        TreeMap<Date, List<Lesson>> lessonsGroupedByStarttime = lessons.getLessonsGroupedByStarttime();
        Iterator iterator = lessonsGroupedByStarttime.navigableKeySet().iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Date date1 = (Date) iterator.next();
            if (iterator.hasNext()) {
                Date date2 = (Date) iterator.next();
                assertTrue(date1.getTime() < date2.getTime());
                for (Lesson lesson : lessonsGroupedByStarttime.get(date1)) {
                    assertTrue(lesson.getStarttimeExact().getTime() >= date1.getTime() &&
                            lesson.getStarttimeExact().getTime() < date1.getTime() + (60 * 60 * 1000));
                }
                i++;
            }
            i++;
        }
        assertEquals(lessonsGroupedByStarttime.size(), i);
    }
}