package com.japhdroid.superfit2;

import java.util.Date;

/**
 * Created by User on 04.12.2015.
 */
public class Course {

    private int id;
    private String title;
    private String description;
    private Floor floor; // 0 = Kurs, 1 = Teamtraining
    private int duration;
    private String imageUrl;
    private Date imageUpdatedAt;

    public Course(int id, String title, String description, Floor floor, int duration, String imageUrl, Date imageUpdatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.floor = floor;
        this.duration = duration;
        this.imageUrl = imageUrl;
        this.imageUpdatedAt = imageUpdatedAt;
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
}
