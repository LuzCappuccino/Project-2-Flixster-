package com.example.luzcamacho.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    /* declare some constants */
    //base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";
    // the API key
    public final static String API_KEY = "3d1749a21621bb72c0069b15c4db3c48";
    //TODO: Move this all into the R folder later (secure location)

    // tagging for all errors in this activity
    public final static String tag = "MovieListActivity";

    AsyncHttpClient client;

    //base url for loading image
    String ImageBaseURL;
    //poster size for images
    String PosterSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //instantiation the client
        client = new AsyncHttpClient();
        //calling our configuration method
        getConfig();
    }

    private void getConfig()
    {
        String url = API_BASE_URL + "/configuration";
        //set up the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, API_KEY);
        //execute a GET request, expect a JSON response
        Log.e("Debug: ", url);
        client.get(url, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //getting image base URL
                try {
                    JSONObject images = response.getJSONObject("images");
                    //attempting to get the image base url
                    ImageBaseURL = images.getString("secure_base_url");

                    // attempting to get the expected poster size
                    JSONArray PosterSizeOps = images.getJSONArray("poster_sizes");
                    PosterSize = PosterSizeOps.optString(3, "w342");
                } catch (JSONException e) {
                    logError("Failed parsing config", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed getting configuration", throwable, true);
            }
        });
    }

    // handle errors, alert user
    private void logError(String message, Throwable error, boolean show)
    {
        Log.e(tag, message, error);
        if(show == true){
            //show a long toast
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        }
    }
}
