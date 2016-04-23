package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by User on 24.01.2016.
 */
public class Lessons {

    private String[] data;
    private List<Lesson> lessons;
    private Map<Course, List<Lesson>> lessonCollections;
    private List<Course> sortingByName;
    private TreeMap<Date, List<Lesson>> lessonsGroupedByStarttime;
    private Studios studios;
    private Courses courses;

    public Lessons(String[] data, Studios studios, Courses courses) {
        this.data = data;
        this.studios = studios;
        this.courses = courses;
        createLessons();
        createLessonCollections();
    }

    private void createLessonCollections() {
        lessonCollections = new HashMap<Course, List<Lesson>>();
        for (Lesson lesson : lessons) {
            Course course = lesson.getCourse();
            if (!lessonCollections.containsKey(course))
                lessonCollections.put(course, new ArrayList<Lesson>());
            List<Lesson> lessonCollection = lessonCollections.get(course);
            lessonCollection.add(lesson);
        }
        createSortingByName();
        createLessonsGroupedByStarttime();
    }

    private void createSortingByName() {
        List<Course> courses = Courses.getCourses();
        sortingByName = new ArrayList<>();
        for (Course course : courses) {
            List<Lesson> lessonCollection = lessonCollections.get(course);
            if (lessonCollection != null)
                sortingByName.add(course);
        }
        Collections.sort(sortingByName);
    }

    private void createLessonsGroupedByStarttime() {
        lessonsGroupedByStarttime = new TreeMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        calendar.setTimeInMillis(0);
        calendar.set(year, month, day, hour, 0);
        Date referenceDate = calendar.getTime();
        Date startHour = calendar.getTime();
        while (calendar.getTimeInMillis() - referenceDate.getTime() < (48 * 60 * 60 * 1000)) {
            calendar.setTimeInMillis(calendar.getTimeInMillis() + (30 * 60 * 1000));
            Date endHour = calendar.getTime();

            List<Lesson> lessonsForPeriod = getLessonsForPeriod(startHour, endHour);
            if (lessonsForPeriod != null)
                lessonsGroupedByStarttime.put(startHour, lessonsForPeriod);

            startHour = calendar.getTime();
        }
    }

    private List<Lesson> getLessonsForPeriod(Date startHour, Date endHour) {
        List<Lesson> lessonsForPeriod = new ArrayList<>();
        for (Lesson lesson : lessons) {
            Date lessonStarttime = lesson.getStarttimeExact();
            if (lessonStarttime.getTime() >= startHour.getTime() && lessonStarttime.getTime() < endHour.getTime())
                lessonsForPeriod.add(lesson);
        }
        if (lessonsForPeriod.size() == 0)
            return null;
        return lessonsForPeriod;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Map<Course, List<Lesson>> getLessonCollections() {
        return lessonCollections;
    }

    public List<Course> getSortingByName() {
        return sortingByName;
    }

    public TreeMap<Date, List<Lesson>> getLessonsGroupedByStarttime() {
        return lessonsGroupedByStarttime;
    }

    public Lesson getLessonById(int id) {
        for (Lesson lesson : lessons)
            if (lesson.getId() == id)
                return lesson;
        return null;
    }

    private void createLessons() {
        lessons = new ArrayList<>();
        for (String _data : data)
            createLessons(_data);
        Collections.sort(lessons);
    }

    private void createLessons(String _data) {
        try {
            JSONArray json = new JSONArray(_data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject element = json.getJSONObject(i);
                int id = element.getInt("id");
                Studio studio = studios.getStudioById(element.getInt("studio_id"));
                Course course = courses.getCourseById(element.getInt("course_id"));
                boolean isEnglish = false;
                if (course.hasParent()) {
                    course = course.getParent();
                    isEnglish = true;
                }
                String starttime = element.getString("starttime");
                Date starttime_asDate = DateTimeParser.getDateFromString(starttime);
                Calendar starttimeCal = Calendar.getInstance();
                starttimeCal.setTime(starttime_asDate);
                Lesson.Weekday weekday_asWeekday = null;
                switch (starttimeCal.get(Calendar.DAY_OF_WEEK)) {
                    case 1:
                        weekday_asWeekday = Lesson.Weekday.SUNDAY;
                        break;
                    case 2:
                        weekday_asWeekday = Lesson.Weekday.MONDAY;
                        break;
                    case 3:
                        weekday_asWeekday = Lesson.Weekday.TUESDAY;
                        break;
                    case 4:
                        weekday_asWeekday = Lesson.Weekday.WEDNESDAY;
                        break;
                    case 5:
                        weekday_asWeekday = Lesson.Weekday.THURSDAY;
                        break;
                    case 6:
                        weekday_asWeekday = Lesson.Weekday.FRIDAY;
                        break;
                    case 7:
                        weekday_asWeekday = Lesson.Weekday.SATURDAY;
                }
                starttime_asDate = DateTimeParser.getTimeFromString(starttime);
                int capacity = element.getInt("capacity");
                Lesson.Capacity capacity_asCapacity = null;
                switch (capacity) {
                    case 1:
                        capacity_asCapacity = Lesson.Capacity.GREEN;
                        break;
                    case 2:
                        capacity_asCapacity = Lesson.Capacity.YELLOW;
                        break;
                    case 3:
                        capacity_asCapacity = Lesson.Capacity.RED;
                }
                String updatedAt = element.getString("updated_at");
                Date updatedAt_asDate = DateTimeParser.getDateFromString(updatedAt);
                Lesson newLesson = new Lesson(id, studio, course, capacity_asCapacity, starttime_asDate, weekday_asWeekday, updatedAt_asDate, isEnglish);
                int conflictingLessonIndex = getConflictingLessonIndex(newLesson);
                if (conflictingLessonIndex >= 0) {
                    Lesson conflictingLesson = lessons.get(conflictingLessonIndex);
                    if (newLesson.getUpdatedAt().getTime() > conflictingLesson.getUpdatedAt().getTime()) {
                        lessons.remove(conflictingLessonIndex);
                        lessons.add(newLesson);
                    }
                } else
                    lessons.add(newLesson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int getConflictingLessonIndex(Lesson newLesson) {
        for (int i = 0; i < lessons.size(); i++) {
            Lesson conflictingLesson = lessons.get(i);
            if ((conflictingLesson.getStudio().getId() == newLesson.getStudio().getId()) &&
                    (conflictingLesson.getCourse().getFloor().ordinal() == newLesson.getCourse().getFloor().ordinal()) &&
                    lessonTimesAreOverlapping(newLesson, conflictingLesson))
                return i;
        }
        return -1;
    }

    private boolean lessonTimesAreOverlapping(Lesson lesson1, Lesson lesson2) {
        long starttimeLesson1Millis = lesson1.getStarttimeExact().getTime();
        long endtimeLesson1Millis = starttimeLesson1Millis + (lesson1.getCourse().getDuration() * 60 * 1000);
        long starttimeLesson2Millis = lesson2.getStarttimeExact().getTime();
        long endtimeLesson2Millis = starttimeLesson2Millis + (lesson2.getCourse().getDuration() * 60 * 1000);
        return starttimeLesson1Millis < endtimeLesson2Millis && starttimeLesson2Millis < endtimeLesson1Millis;
    }

    public List<Lesson> getLessons(Studio[] studios) {
        List<Lesson> _lessons = new ArrayList<>();
        for (Lesson lesson : lessons)
            for (Studio studio : studios)
                if (lesson.getStudio().getId() == studio.getId())
                    _lessons.add(lesson);
        return _lessons;
    }

    public List<Lesson> getLessons(Studio[] studios, boolean futureCourses) {
        List<Lesson> _lessons = getLessons(studios);
        if (futureCourses)
            Collections.sort(_lessons);
        return _lessons;
    }

    public List<Lesson> getLessons(Studio[] studios, boolean futureCourses, boolean includeRunning) {
        return null; //TODO implement
    }

    public List<Lesson> getSurroundingLessons(Lesson lesson) {
        List<Lesson> lessonsForStudio = getLessons(new Studio[]{lesson.getStudio()});
        for (int i = lessonsForStudio.size() - 1; i >= 0; i--) {
            if (!lessonIsWithinTimeRange(lesson, lessonsForStudio.get(i)) ||
                    lessonTimesAreOverlapping(lesson, lessonsForStudio.get(i)))
                lessonsForStudio.remove(i);
        }
        return lessonsForStudio;
    }

    private boolean lessonIsWithinTimeRange(Lesson checkedLesson, Lesson surroundingLesson) {
        long gapInMillis = 0;
        if (surroundingLesson.getEndtime().getTime() < checkedLesson.getStarttimeExact().getTime())
            gapInMillis = checkedLesson.getStarttimeExact().getTime() - surroundingLesson.getEndtime().getTime();
        else if (surroundingLesson.getStarttimeExact().getTime() >= checkedLesson.getEndtime().getTime())
            gapInMillis = surroundingLesson.getStarttimeExact().getTime() - checkedLesson.getEndtime().getTime();
        if (gapInMillis <= (15 * 60 * 1000))
            return true;
        return false;
    }
}
