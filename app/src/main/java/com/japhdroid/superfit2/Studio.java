package com.japhdroid.superfit2;

/**
 * Created by User on 23.01.2016.
 */
public class Studio {

    private int id;
    private String title;
    private String street;
    private String city;
    private String zipcode;

    public Studio(int id, String title, String street, String city, String zipcode) {
        this.id = id;
        this.title = title;
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
