package com.japhdroid.superfit2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 24.01.2016.
 */
public class Studios {

    private String data;
    private List<Studio> studios;

    public Studios(String data) {
        this.data = data;
        createStudios();
    }

    public List<Studio> getStudios() {
        return studios;
    }

    public Studio getStudioById(int id) {
        for (Studio studio : studios)
            if (studio.getId() == id)
                return studio;
        return null;
    }

    private void createStudios() {
        studios = new ArrayList<>();
        try {
            JSONArray json = new JSONArray(data);
            for (int i = 0; i < json.length(); i++) {
                JSONObject element = json.getJSONObject(i);
                int id = element.getInt("id");
                String title = element.getString("title");
                String street = element.getString("street");
                String zipcode = element.getString("zipcode");
                String city = element.getString("city");
                studios.add(new Studio(id, title, street, zipcode, city));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
