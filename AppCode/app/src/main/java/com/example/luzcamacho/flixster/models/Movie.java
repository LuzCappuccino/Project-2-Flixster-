package com.example.luzcamacho.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luzcamacho on 6/27/18.
 */

public class Movie {
    private String Title;
    private String Overview;
    private String ImagePath;

    public Movie(JSONObject object) throws JSONException
    {
        Title = object.getString("title");
        Overview = object.getString("overview");
        ImagePath = object.getString("poster_path");
    }

    public String getTitle() {
        return Title;
    }

    public String getOverview() {
        return Overview;
    }

    public String getImagePath() {
        return ImagePath;
    }
}
