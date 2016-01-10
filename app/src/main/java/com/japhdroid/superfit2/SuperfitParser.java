package com.japhdroid.superfit2;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitParser {

    private List<SuperfitCourse> courseList = new ArrayList<>();
    private String url;
    private int daysInTheFuture;

    public SuperfitParser(String url, int daysInTheFuture, boolean autoLoadData) {
        this.url = url;
        this.daysInTheFuture = daysInTheFuture;
        if (!CheckUrlIsValid())
            throw new IllegalArgumentException("The supplied URL is not valid.");
        this.url = GetUrlForDay(this.url, daysInTheFuture);
        if (autoLoadData)
            LoadData();
    }

    public List<SuperfitCourse> getCourseList() {
        return courseList;
    }

    private boolean CheckUrlIsValid() {
        // http://m.mysuperfit.com/kursplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/kursplaene/berlin-mitte
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-friedrichshain
        // http://m.mysuperfit.com/teamtrainingsplaene/berlin-mitte
        // /di /mi /do /fr /sa /so

        String[] basicUrls = {"http://m.mysuperfit.com/kursplaene/",
                "http://m.mysuperfit.com/teamtrainingsplaene/"};
        String[] locationUrlPart = {"berlin-friedrichshain",
                "berlin-mitte"};
        String[] dateUrlPart = {"/di", "/mi", "/do", "/fr", "/sa", "/so"};

        for (String basic : basicUrls) {
            if (url.startsWith(basic))
                for (String location : locationUrlPart) {
                    if (url.equalsIgnoreCase(basic + location)) {
                        return true;
                    }
                    for (String date : dateUrlPart) {
                        if (url.equalsIgnoreCase(basic + location + date)) {
                            return true;
                        }
                    }
                }
        }

        return false;
    }

    private String GetUrlForDay(String urlForDay, int daysInTheFuture) {
        if (daysInTheFuture < 0 || daysInTheFuture > 6)
            throw new IllegalArgumentException("Days in the future must be between 0 and 6.");
        String[] dateUrlPart = {"/di", "/mi", "/do", "/fr", "/sa", "/so"};
        for (String dateAppendix : dateUrlPart) {
            if (urlForDay.endsWith(dateAppendix)) {
                int position = urlForDay.lastIndexOf("/");
                urlForDay = urlForDay.substring(0, position);
            }
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String[] dateUrlPart2 = {"/so", "", "/di", "/mi", "/do", "/fr", "/sa"};
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1 = sunday .. 7 = saturday
        dayOfWeek += daysInTheFuture;
        if (dayOfWeek > 7)
            dayOfWeek -= 7;
        return urlForDay + dateUrlPart2[dayOfWeek - 1];
    }

    public void LoadData() {
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
            return; // TODO throw exception
        }
        List<Node> nodes = doc.getElementById("content").childNodes();
        for (Node node : nodes) {
            String nodeStr = node.toString();
            if (!nodeStr.contains("kpcelltime"))
                continue;

            int timeIndex = nodeStr.indexOf("kpcelltime");
            String time = nodeStr.substring(timeIndex + 12, timeIndex + 17);

            int courseStart = nodeStr.indexOf("img src");
            courseStart = nodeStr.indexOf("\"", courseStart) + 1;
            int courseEnd = nodeStr.indexOf("\"", courseStart);
            String courseStr = nodeStr.substring(courseStart, courseEnd);
            courseStart = courseStr.lastIndexOf("/");
            courseStr = courseStr.substring(courseStart + 1, courseStr.length());

            SuperfitCourse course = new SuperfitCourse();
            String type = null;
            if (url.contains("kursplaene")) {
                type = "Kurs";
            } else if (url.contains("teamtrainingsplaene")) {
                type = "Team";
            }
            if (url.contains("berlin-friedrichshain"))
                course.setLocation("FR");
            else if (url.contains("berlin-mitte"))
                course.setLocation("MI");
            else
                throw new IllegalArgumentException("Kursort nicht gefunden.");
            course.setType(type);
            course.setName(courseStr);
            course.setTime(parseTime(time, daysInTheFuture));
            courseList.add(course);
        }
    }

    private Date parseTime(String time, int addNoOfDays) {
        Date suppliedDate = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
        try {
            suppliedDate = simpleDateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(suppliedDate);
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        int minute = cal.get(Calendar.MINUTE);
        cal.set(year, month, day, hour, minute);

        return new Date(cal.getTimeInMillis() + addNoOfDays * (24 * 3600 * 1000));
    }
}
