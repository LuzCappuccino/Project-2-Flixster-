package com.example.luzcamacho.flixster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.luzcamacho.flixster.models.Config;
import com.example.luzcamacho.flixster.models.Movie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class MovieListActivity extends AppCompatActivity {

    /* declare some constants */
    //base URL for the API
    public final static String API_BASE_URL = "https://api.themoviedb.org/3";
    // the parameter name for the API key
    public final static String API_KEY_PARAM = "api_key";
    //TODO: Move this all into the R folder later (secure location)

    // tagging for all errors in this activity
    public final static String tag = "MovieListActivity";

    AsyncHttpClient client;

    //container with all movie info
    ArrayList<Movie> movies;
    //set up recycler
    RecyclerView rvMovies;
    // set adapter wired to the recycler
    MovieAdapter adapter;
    //image config
    Config config;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        //instantiation the client
        client = new AsyncHttpClient();
        //calling our configuration method
        movies = new ArrayList<>();
        //initialize the adapter, the array list cannot be reinitialized after this point
        adapter = new MovieAdapter(movies);

        //resolve reference from the recycler and connect layout manager and the adapter
        rvMovies = (RecyclerView)findViewById(R.id.rvMovies);
        rvMovies.setLayoutManager(new LinearLayoutManager(this));
        rvMovies.setAdapter(adapter);

        getConfig();
    }

    private void getConfig()
    {
        String url = API_BASE_URL + "/configuration";
        //set up the request parameters
        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request, expect a JSON response
        client.get(url, params, new JsonHttpResponseHandler()
        {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                //getting image base URL
                try {
                    config = new Config(response);
                    //logging necessary info
                    Log.i(tag, String.format("Config loaded with image url %s and poster size %s",
                            config.getImageBaseURL(), config.getPosterSize()));
                    //TODO: pass to the adapter
                    adapter.setConfig(config);
                    // get the now playing list
                    getNowPlaying();
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

    private void getNowPlaying()
    {
        //make the network call
        String url = API_BASE_URL + "/movie/now_playing";

        RequestParams params = new RequestParams();
        params.put(API_KEY_PARAM, getString(R.string.api_key));
        //execute a GET request, expect a JSON response
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                /* time to parse the JSON response */
                try {
                    JSONArray MovieResults = response.getJSONArray("results");

                    for(int i = 0; i < MovieResults.length(); i++){
                        movies.add(new Movie(MovieResults.getJSONObject(i)));
                        // let the adapter know they were changes to our array list
                        adapter.notifyItemInserted(movies.size() - 1);
                    }
                    Log.i(tag, String.format("Movie database initialized. Results: %s", MovieResults.length()));
                } catch (JSONException e) {
                    logError("Failed to parse now_playing response", e, true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                logError("Failed to get to now_playing endpoint", throwable, true);
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
