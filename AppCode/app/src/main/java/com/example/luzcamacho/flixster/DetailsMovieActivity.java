package com.example.luzcamacho.flixster;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.luzcamacho.flixster.models.Config;
import com.example.luzcamacho.flixster.models.GlideApp;
import com.example.luzcamacho.flixster.models.Movie;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class DetailsMovieActivity extends AppCompatActivity {

    // movie to display
    Movie movie;
    // view titles and declarations
    TextView tvTitle;
    TextView tvOverview;
    ImageView ivPoster;
    RatingBar rbVotingAvg;
    // stuff for glide
    public static Config config;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_movie);
        // resolve the view objects
        tvTitle = (TextView) findViewById(R.id.tvTitleClick);
        tvOverview = (TextView) findViewById(R.id.tvOverviewClick);
        ivPoster = (ImageView) findViewById(R.id.ivPosterClick);
        rbVotingAvg = (RatingBar) findViewById(R.id.ratingBar);

        // unwrap the movie passed via the intent , using simple name as a key
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra(Movie.class.getSimpleName()));
        Log.d("MovieDetailsActivity", String.format("Showing details for '%s'", movie.getTitle()));

        // set title, poster, overview and avg
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());

        float voteAverage = movie.getVoteAvg().floatValue();
        rbVotingAvg.setRating(voteAverage = voteAverage > 0 ? voteAverage / 2.0f : voteAverage);
        // get image url and stuff
        String imageURL = Config.getImageUrl(Config.getPosterSize(), movie.getImagePath());


        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        GlideApp.with(getApplicationContext())
                .load(imageURL)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .transform(new RoundedCornersTransformation(radius, margin))
                .error(R.drawable.flicks_movie_placeholder)
                .into(ivPoster);
    }

}
