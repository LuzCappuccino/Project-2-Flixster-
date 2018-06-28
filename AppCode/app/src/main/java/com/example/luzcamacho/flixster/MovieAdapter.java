package com.example.luzcamacho.flixster;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.luzcamacho.flixster.models.Movie;

import java.util.ArrayList;

/**
 * Created by luzcamacho on 6/27/18.
 */

public class MovieAdapter {
    //our list of movies
    ArrayList<Movie> movies;

    //init with list
    public MovieAdapter(ArrayList<Movie> movies) {
        this.movies = movies;
    }

    //create the view holder as a static inner class
    public class ViewHolder extends RecyclerView.ViewHolder
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
}
