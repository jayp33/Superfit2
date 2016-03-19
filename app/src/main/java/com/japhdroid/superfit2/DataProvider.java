package com.japhdroid.superfit2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by User on 19.03.2016.
 */
public class DataProvider {

    private static Studios studios;
    private static Courses courses;
    private static Lessons lessons;

    enum DataType {
        STUDIOS, COURSES, LESSONS
    }

    public static Studios getStudios() {
        return studios;
    }

    public static Courses getCourses() {
        return courses;
    }

    public static Lessons getLessons() {
        return lessons;
    }

    public static void LoadData(Map<DataType, String[]> urls) {
        if (studios == null) {
            String studioUrl = urls.get(DataType.STUDIOS)[0];
            LoadStudios(studioUrl);
        }
        if (courses == null) {
            String courseUrl = urls.get(DataType.COURSES)[0];
            LoadCourses(courseUrl);
        }
        LoadLessons(urls.get(DataType.LESSONS));
    }

    private static String getJSON(String url, int timeout) {
        HttpURLConnection c = null;
        try {
            URL u = new URL(url);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setRequestProperty("Content-length", "0");
            c.setUseCaches(false);
            c.setAllowUserInteraction(false);
            c.setConnectTimeout(timeout);
            c.setReadTimeout(timeout);
            c.connect();
            int status = c.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line + "\n");
                    }
                    br.close();
                    return sb.toString();
            }

        } catch (MalformedURLException ex) {
            Logger.getLogger("MalformedURLException");
        } catch (IOException ex) {
            Logger.getLogger("IOException");
        } finally {
            if (c != null) {
                try {
                    c.disconnect();
                } catch (Exception ex) {
                    Logger.getLogger("Exception");
                }
            }
        }
        return null;
    }

    private static void LoadStudios(String url) {
        String studiosHtml = getJSON(url, 10000);
        studios = new Studios(studiosHtml);
    }

    private static void LoadCourses(String url) {
        String coursesHtml = getJSON(url, 10000);
        courses = new Courses(coursesHtml);
    }

    private static void LoadLessons(String[] urls) {
        String[] lessonData = new String[urls.length];
        for (int i = 0; i < urls.length; i++) {
            String lessonsHtml = getJSON(urls[i], 10000);
            lessonData[i] = lessonsHtml;
        }
        lessons = new Lessons(lessonData, studios, courses);
    }
}
