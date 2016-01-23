package com.japhdroid.superfit2;

import java.util.Date;

/**
 * Created by User on 23.01.2016.
 */
public class Lesson {

    private int id;
    private Studio studio;
    private Course course;
    private Capacity capacity; // 1 = green, 2 = yellow, 3 = red
    private Weekday weekday; // 0 = Monday .. 6 = Sunday
    private Date starttime; // ignore Date, use Time in combination with weekday
    private Date endtime;

    public Lesson(int id, Studio studio, Course course, Capacity capacity, Date starttime, Weekday weekday) {
        this.id = id;
        this.studio = studio;
        this.course = course;
        this.capacity = capacity;
        this.weekday = weekday;
        this.starttime = starttime;
        // TODO: Calculate endtime (Course.duration)
    }

    enum Capacity {
        GREEN, YELLOW, RED
    }

    enum Weekday {
        MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY
    }
}
