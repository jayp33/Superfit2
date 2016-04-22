package com.japhdroid.superfit2;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by User on 23.01.2016.
 */
public class Lesson implements Comparable<Lesson> {

    private int id;
    private Studio studio;
    private Course course;
    private Capacity capacity; // 1 = green, 2 = yellow, 3 = red
    private Weekday weekday; // 1 = Sunday .. 7 = Saturday
    private Date starttime; // ignore Date, use Time in combination with weekday
    private Date updatedAt;
    private boolean isEnglish;
    private Date starttimeExact;
    private Date endtime;

    public Lesson(int id, Studio studio, Course course, Capacity capacity, Date starttime, Weekday weekday, Date updatedAt, boolean isEnglish) {
        this.id = id;
        this.studio = studio;
        this.course = course;
        this.capacity = capacity;
        this.weekday = weekday;
        this.starttime = starttime;
        this.updatedAt = updatedAt;
        this.isEnglish = isEnglish;
        calculateExactTimes();
    }

    enum Capacity {
        GREEN, YELLOW, RED
    }

    enum Weekday {
        MONDAY(2), TUESDAY(3), WEDNESDAY(4), THURSDAY(5), FRIDAY(6), SATURDAY(7), SUNDAY(1);
        private final int value;

        Weekday(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public int getId() {
        return id;
    }

    public Studio getStudio() {
        return studio;
    }

    public Course getCourse() {
        return course;
    }

    public Capacity getCapacity() {
        return capacity;
    }

    public Weekday getWeekday() {
        return weekday;
    }

    public Date getStarttime() {
        return starttime;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    public Date getStarttimeExact() {
        return starttimeExact;
    }

    public Date getEndtime() {
        return endtime;
    }

    public boolean lessonIsOver() {
        if (lessonIsToday() && new Date().getTime() > endtime.getTime())
            return true;
        return false;
    }

    @Override
    public String toString() {
        String starttimeStr = starttime.toString().substring(11, 19);
        return id +
                ",studio=" + studio.getId() +
                "," + course.getId() + ":" + course.getTitle() +
                "," + course.getFloor() +
                "," + capacity +
                "," + weekday +
                "," + starttimeStr;
    }

    @Override
    public int compareTo(Lesson another) {
        if (starttimeExact.getTime() == another.starttimeExact.getTime())
            if (course.getFloor() != another.course.getFloor())
                if (course.getFloor() == Course.Floor.KURS)
                    return -1;
                else
                    return 1;
            else
                return course.getTitleUppercase().compareTo(another.course.getTitleUppercase());

        return starttimeExact.compareTo(another.starttimeExact);
    }

    private void calculateExactTimes() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK); // 1 = sunday .. 7 = saturday
        int daysInTheFuture = weekday.getValue() - dayOfWeek;
        if (daysInTheFuture < 0)
            daysInTheFuture += 7;
        cal.setTimeInMillis(new Date().getTime() + (daysInTheFuture * 24 * 60 * 60 * 1000));
        Calendar lessonCal = Calendar.getInstance();
        lessonCal.setTime(starttime);
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTimeInMillis(0);
        cal.set(year, month, day, lessonCal.get(Calendar.HOUR_OF_DAY), lessonCal.get(Calendar.MINUTE), 0);
        starttimeExact = cal.getTime();
        cal.setTimeInMillis(cal.getTimeInMillis() + (course.getDuration() * 60 * 1000));
        endtime = cal.getTime();
    }

    private boolean lessonIsToday() {
        Date lessonDate = starttimeExact;
        Date today = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(lessonDate);
        int lessonWeekday = cal.get(Calendar.DAY_OF_WEEK);
        cal.setTime(today);
        int todayWeekday = cal.get(Calendar.DAY_OF_WEEK);
        return lessonWeekday == todayWeekday;
    }
}
