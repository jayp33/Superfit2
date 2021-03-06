package com.japhdroid.superfit2;

import java.util.Date;

/**
 * Created by User on 04.12.2015.
 */
public class Course implements Comparable<Course> {

    private int id;
    private String title;
    private String titleUppercase;
    private String description;
    private Floor floor; // 0 = Kurs, 1 = Teamtraining
    private int duration;
    private String imageUrl;
    private Date imageUpdatedAt;
    private boolean isEnglish;
    private Course parent;

    public Course(int id, String title, String description, Floor floor, int duration, String imageUrl, Date imageUpdatedAt) {
        this.id = id;
        this.title = title;
        this.titleUppercase = title.toUpperCase();
        this.description = description;
        this.floor = floor;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.imageUpdatedAt = imageUpdatedAt;
        setEnglish(title);
    }

    @Override
    public String toString() {
        return title;
    }

    @Override
    public int compareTo(Course another) {
        return titleUppercase.compareTo(another.titleUppercase);
    }

    enum Floor {
        KURS, TEAMTRAINING
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleUppercase() {
        return titleUppercase;
    }

    public String getDescription() {
        return description;
    }

    public Floor getFloor() {
        return floor;
    }

    public int getDuration() {
        return duration;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public Date getImageUpdatedAt() {
        return imageUpdatedAt;
    }

    public boolean isEnglish() {
        return isEnglish;
    }

    private void setEnglish(String title) {
        if (title.endsWith(" (english)")) {
            isEnglish = true;
        }
    }

    public Course getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public void setParent(Course parent) {
        this.parent = parent;
    }
}
