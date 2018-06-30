package com.example.luzcamacho.flixster;

import android.os.Bundle;
import android.util.Log;

import com.example.luzcamacho.flixster.models.Movie;
import com.example.luzcamacho.flixster.models.YouTubeInfo;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class MovieTrailerActivity extends YouTubeBaseActivity {

    AsyncHttpClient client;
    Movie movie;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_trailer);
        client = new AsyncHttpClient();
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        // temporary test video id -- TODO replace with movie trailer video id
        final Integer videoId = movie.getId();

        // resolve the player view from the layout
        final YouTubePlayerView playerView = (YouTubePlayerView) findViewById(R.id.player);

        String url = MovieListActivity.API_BASE_URL + "/movie/" + videoId + "/videos";
        //set up the request parameters
        Log.e("TrailerActivity", url);
        RequestParams params = new RequestParams();
        params.put(MovieListActivity.API_KEY_PARAM, getString(R.string.api_key));

        //execute a GET request, expect a JSON response
        client.get(url, params, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response){
                YouTubeInfo info = new YouTubeInfo(response);
                final String TrailerId = info.getValidKey();
                Log.e("TrailerActivity", "Valid Key: " + TrailerId);
                playerView.initialize(getString(R.string.yt_api_key), new YouTubePlayer.OnInitializedListener(){

                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                        youTubePlayer.cueVideo(TrailerId);
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                        Log.e("TrailerActivity", "Coudln't load vid");
                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject j) {
                Log.e("TrailerActivity", j.toString());
            }
        });

    }
}
