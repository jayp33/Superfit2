package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 24.01.2016.
 */
public class Lessons {

    private String data;
    private List<Lesson> lessons;
    private Studios studios;
    private Courses courses;

    public Lessons(String data, Studios studios, Courses courses) {
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
        try {
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject element = json.getJSONObject(i);
                int id = element.getInt("id");
                Studio studio = studios.getStudioById(element.getInt("studio_id"));
                Course course = courses.getCourseById(element.getInt("course_id"));
                String starttime = element.getString("starttime");
                Date starttime_asDate = DateTimeParser.getTimeFromString(starttime);
                int weekday = element.getInt("weekday");
                Lesson.Weekday weekday_asWeekday = null;
                switch (weekday) {
                    case 0:
                        weekday_asWeekday = Lesson.Weekday.MONDAY;
                        break;
                    case 1:
                        weekday_asWeekday = Lesson.Weekday.TUESDAY;
                        break;
                    case 2:
                        weekday_asWeekday = Lesson.Weekday.WEDNESDAY;
                        break;
                    case 3:
                        weekday_asWeekday = Lesson.Weekday.THURSDAY;
                        break;
                    case 4:
                        weekday_asWeekday = Lesson.Weekday.FRIDAY;
                        break;
                    case 5:
                        weekday_asWeekday = Lesson.Weekday.SATURDAY;
                        break;
                    case 6:
                        weekday_asWeekday = Lesson.Weekday.SUNDAY;
                }
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
                lessons.add(new Lesson(id, studio, course, capacity_asCapacity, starttime_asDate, weekday_asWeekday));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
