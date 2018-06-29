package com.example.luzcamacho.flixster.models;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by luzcamacho on 6/27/18.
 */

@Parcel //annotation that states that object is parcelable
public class Movie {
    //fields must be public for parceler
    String Title;
    String Overview;
    String ImagePath;
    Double VoteAvg;

    public Movie() { }

    public Movie(JSONObject object) throws JSONException
    {
        Title = object.getString("title");
        Overview = object.getString("overview");
        ImagePath = object.getString("poster_path");
        VoteAvg= object.getDouble("vote_average");
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

    public Double getVoteAvg() { return VoteAvg; }
}
