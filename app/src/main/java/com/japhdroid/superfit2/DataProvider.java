package com.japhdroid.superfit2;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by User on 19.03.2016.
 */
public class DataProvider {

    private static Context context;
    private static Studios studios;
    private static Courses courses;
    private static Lessons lessons;
    private static boolean dataIsValid = true;

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

    public static void LoadData(Context context, Map<DataType, String[]> urls) {
        DataProvider.context = context;
        if (studios == null) {
            String studioUrl = urls.get(DataType.STUDIOS)[0];
            LoadStudios(studioUrl);
        }
        if (courses == null) {
            String courseUrl = urls.get(DataType.COURSES)[0];
            LoadCourses(courseUrl);
        }
        LoadLessons(urls.get(DataType.LESSONS));
        dataIsValid = true;
    }

    public static void invalidateData() {
        studios = null;
        courses = null;
        lessons = null;
        dataIsValid = false;
    }

    private static String getJSON(String url, int timeout) {
        String json = getJSONFromCache(url);
        if (json != null) {
            return json;
        }
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

                    storeJSONToCache(url, sb.toString());

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

    private static void storeJSONToCache(String url, String json) {
        url = formatUrlForCache(url);
        JSONCache jsonCache = new JSONCache();
        jsonCache.setUrl(url);
        jsonCache.setJson(json);
        try {
            InternalStorage.writeObject(context, url, jsonCache);
            Log.d("JSON", "JSON stored to cache for " + url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Preferences.setLastDownloadTime(context, new Date().getTime());
    }

    private static String getJSONFromCache(String url) {
        if (!isCacheValid())
            return null;
        url = formatUrlForCache(url);
        JSONCache json = null;
        try {
            json = (JSONCache) InternalStorage.readObject(context, url);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (json != null) {
            Log.d("JSON", "JSON loaded from cache for " + url);
            return json.getJson();
        }
        return null;
    }

    private static boolean isCacheValid() {
        long cacheTimeout = Preferences.getCacheTimeout(context);
        if (!dataIsValid)
            cacheTimeout = 0;
        long lastDownloadTime = Preferences.getLastDownloadTime(context);
        long currentTime = new Date().getTime();
        Log.d("JSON", "Data still valid for " + ((lastDownloadTime + cacheTimeout) - currentTime) + " ms");

        final int dataAge = (int) ((currentTime - lastDownloadTime) / 1000 / 60);
        final boolean cacheIsValid = currentTime < lastDownloadTime + cacheTimeout;

        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (cacheIsValid)
                    Toast.makeText(DataProvider.context, "Using cached data\nAge: " + dataAge + " minutes", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(DataProvider.context, "Data loaded from server", Toast.LENGTH_SHORT).show();
            }
        });

        return cacheIsValid;
    }

    private static String formatUrlForCache(String url) {
        String[] urlSegments = url.split("/");
        return urlSegments[urlSegments.length - 1];
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
