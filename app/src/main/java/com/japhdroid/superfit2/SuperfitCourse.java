package com.japhdroid.superfit2;

import java.util.Date;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitCourse implements Comparable<SuperfitCourse> {

    private String location;
    private String type;
    private Date time;
    private String name;
    private String filename;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    @Override
    public int compareTo(SuperfitCourse another) {
        if (time.getTime() == another.time.getTime())
            if (type != another.type)
                if (type == "Kurs")
                    return -1;
                else
                    return 1;

        return this.time.compareTo(another.getTime());
    }
}
