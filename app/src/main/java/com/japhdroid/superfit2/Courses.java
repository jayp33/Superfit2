package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by User on 24.01.2016.
 */
public class Courses {

    private String data;
    static private List<Course> courses;

    public Courses(String data) {
        this.data = data;
        createCourses();
        identifyParentCourses();
    }

    static public List<Course> getCourses() {
        return courses;
    }

    public Course getCourseById(int id) {
        for (Course course : courses)
            if (course.getId() == id)
                return course;
        return null;
    }

    private void createCourses() {
        courses = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject element = json.getJSONObject(i);
                int id = element.getInt("id");
                String title = element.getString("title");
                String description = element.getString("description");
                int floor = element.getInt("floor");
                Course.Floor floor_asFloor = null;
                if (floor == 0)
                    floor_asFloor = Course.Floor.KURS;
                else if (floor == 1)
                    floor_asFloor = Course.Floor.TEAMTRAINING;
                int duration = element.getInt("duration");
                String imageUrl = "http://superfit.navillo.de" + element.getString("image_url");
                String imageUpdatedAt = element.getString("image_updated_at");
                Date imageUpdatedAt_asDate = DateTimeParser.getDateFromString(imageUpdatedAt);
                courses.add(new Course(id, title, description, floor_asFloor, duration, imageUrl, imageUpdatedAt_asDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void identifyParentCourses() {
        for (Course course : courses) {
            if (course.isEnglish()) {
                course.setParent(getParent(course));
            }
        }
    }

    private Course getParent(Course englishCourse) {
        for (Course course : courses) {
            String englishCourseTitle = englishCourse.getTitle();
            englishCourseTitle = englishCourseTitle.substring(0, englishCourseTitle.indexOf(" (english)"));
            if (!course.equals(englishCourse) && !course.isEnglish() &&
                    course.getTitle().equalsIgnoreCase(englishCourseTitle))
                return course;
        }
        throw new NoSuchElementException();
    }
}
