package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 24.01.2016.
 */
public class Courses {

    private String data;
    private List<Course> courses;

    public Courses(String data) {
        this.data = data;
        createCourses();
    }

    public List<Course> getCourses() {
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
                Date imageUpdatedAt_asDate = getDateFromString(imageUpdatedAt);
                courses.add(new Course(id, title, description, floor_asFloor, duration, imageUrl, imageUpdatedAt_asDate));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Date getDateFromString(String imageUpdatedAt) {
        Date dateFromString = null;
        imageUpdatedAt = imageUpdatedAt.replace("T", " ");
        imageUpdatedAt = imageUpdatedAt.replace("Z", "");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            dateFromString = simpleDateFormat.parse(imageUpdatedAt);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        return dateFromString;
    }
}
