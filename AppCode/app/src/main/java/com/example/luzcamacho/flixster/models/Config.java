package com.example.luzcamacho.flixster.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luzcamacho on 6/28/18.
 */

public class Config {
    //base url for loading image
    String ImageBaseURL;
    //poster size for images
    String PosterSize;

    public Config(JSONObject Object) throws JSONException {
        JSONObject images = Object.getJSONObject("images");
        //attempting to get the image base url
        ImageBaseURL = images.getString("secure_base_url");

        // attempting to get the expected poster size
        JSONArray PosterSizeOps = images.getJSONArray("poster_sizes");
        PosterSize = PosterSizeOps.optString(3, "w342");
    }

    //helper function which produces the necessary image urls
    public String getImageUrl(String size, String path) {
        //concetenate everything
        return String.format("%s%s%s", ImageBaseURL, size, path);
    }

    // basic getters
    public String getImageBaseURL(){
        return ImageBaseURL;
    }

    public String getPosterSize(){
        return PosterSize;
    }
}
