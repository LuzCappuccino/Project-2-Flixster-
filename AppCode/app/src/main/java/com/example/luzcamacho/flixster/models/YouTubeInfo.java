package com.example.luzcamacho.flixster.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by luzcamacho on 6/29/18.
 */

public class YouTubeInfo {
    // parsing our JSON object using another thing
    static String ValidKey;

    public YouTubeInfo(JSONObject VideoResults){
        ValidKey = "";
        JSONArray res = null;

        try {
            res = VideoResults.getJSONArray("results");
        } catch (JSONException e) {
            Log.e("TrailerError", String.valueOf(e));
        }
        for(int i = 0; i < res.length(); i++){
            try {
                JSONObject jsonObject = res.getJSONObject(i);
                if(jsonObject.getString("site").equals("YouTube") && jsonObject.getString("type").equals("Trailer")){
                    ValidKey = jsonObject.getString("key");
                    break;
                }
            } catch (JSONException e) {
                Log.e("TrailerError", String.valueOf(e));
            }
        }
    }

    public static String getValidKey() {
        return ValidKey;
    }
}
