package com.japhdroid.superfit2;

/**
 * Created by User on 04.12.2015.
 */
public class SuperfitParser {

    private String url;

    public SuperfitParser(String url) {
        this.url = url;
        if (!CheckUrlIsValid())
            throw new IllegalArgumentException("The supplied URL is not valid.");
    }

    private boolean CheckUrlIsValid() {

        return false;
    }
}
