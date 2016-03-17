package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 24.01.2016.
 */
public class Lessons {

    private String[] data;
    private List<Lesson> lessons;
    private Studios studios;
    private Courses courses;

    public Lessons(String[] data, Studios studios, Courses courses) {
        this.data = data;
        this.studios = studios;
        this.courses = courses;
        createLessons();
    }

    public List<Lesson> getLessons() {
        return lessons;
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
    }

    private void createLessons(String _data) {
        try {
            JSONArray json = new JSONArray(_data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject element = json.getJSONObject(i);
                int id = element.getInt("id");
                Studio studio = studios.getStudioById(element.getInt("studio_id"));
                Course course = courses.getCourseById(element.getInt("course_id"));
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
                Lesson newLesson = new Lesson(id, studio, course, capacity_asCapacity, starttime_asDate, weekday_asWeekday, updatedAt_asDate);
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
}