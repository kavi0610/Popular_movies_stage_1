package com.example.android.popularmovies_stage1_retrofit;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.popularmovies_stage1_retrofit.Model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Adapter to help with recycling the movie poster views
 */

public class CustomMovieAdapter extends RecyclerView.Adapter<CustomMovieAdapter.MovieViewHolder> {
    private final Context context;
    private final List<Movie> movies;

    public CustomMovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.movie_poster,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {

        // Storing the path to get the poster image
        String builtPosterPath = context.getResources().getString(R.string.image_url)+movies.get(position).getPosterPath();
        //Using Picasso to load the image
        Picasso.with(context).load(builtPosterPath).into(holder.Img_movie_poster);

    }

    @Override
    public int getItemCount() {
        if (movies.isEmpty())return 0;
            return movies.size();

    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        final ImageView Img_movie_poster;

        public MovieViewHolder(View view) {
            super(view);

            Img_movie_poster = view.findViewById(R.id.Img_movie_poster);


            // OnClickListener to listen for user clicks and open respective details page
             view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int clickedPosition = getAdapterPosition();
                    Movie movieClicked = movies.get(clickedPosition);
                    launchDetailActivity(movieClicked);
                }
            });
        }

    }

    //Responding to user click and launching new intent with parcelable object
    private void launchDetailActivity(Movie movieClicked){
        Intent intent = new Intent(context,DetailActivity.class);
        intent.putExtra("Movie",movieClicked);
        context.startActivity(intent);

    }
}
