package com.example.luzcamacho.flixster;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luzcamacho.flixster.models.Config;
import com.example.luzcamacho.flixster.models.GlideApp;
import com.example.luzcamacho.flixster.models.Movie;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Created by luzcamacho on 6/27/18.
 */


/* ask what this line is doing later */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    //our list of movies
    ArrayList<Movie> movies;

    Config config;
    Context context;
    //init with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //creates and inflates a new view
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // create the inflater
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        //create the view using the item movie layout
        View movieView = inflater.inflate(R.layout.item_movie, parent, false);
        //return that view
        return new ViewHolder(movieView);
    }

    //associated inflated view to a new item
    //associates the holder to a specific element in the data set
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // get the movie data at the specified position
        Movie movie = movies.get(position);
        // populate the view with the movie data
        holder.tvTitle.setText(movie.getTitle());
        holder.tvOverview.setText(movie.getOverview());

        //build image url using the config object
        String imageURL = config.getImageUrl(config.getPosterSize(), movie.getImagePath());
        //load the image using glide
        int radius = 30; // corner radius, higher value = more rounded
        int margin = 10; // crop margin, set to 0 for corners with no crop
        GlideApp.with(context)
                .load(imageURL)
                .placeholder(R.drawable.flicks_movie_placeholder)
                .transform(new RoundedCornersTransformation(radius, margin))
                .error(R.drawable.flicks_movie_placeholder)
                .into(holder.ivPosterImage);

    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    //create the view holder as a static inner class
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        ImageView ivPosterImage;
        TextView tvTitle;
        TextView tvOverview;

        public ViewHolder(View itemView) {
            super(itemView);
            //lookup up views by id
            ivPosterImage = (ImageView) itemView.findViewById(R.id.ivPoster);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvOverview = (TextView) itemView.findViewById(R.id.tvOverview);
        }
    }

    public Config getConfig() {
        return config;
    }

    public void setConfig(Config config) {
        this.config = config;
    }
}
