package com.japhdroid.superfit2;

import java.io.Serializable;

/**
 * Created by User on 23.06.2016.
 */
public class JSONCache implements Serializable {

    private String url;
    private String json;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }
}
