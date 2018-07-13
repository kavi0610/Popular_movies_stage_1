package com.example.android.popularmovies_stage1_retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies_stage1_retrofit.Model.Movie;
import com.squareup.picasso.Picasso;

/**
 * Class that shows the Movie details
 */

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }


        else{
            //Setting the views
            ImageView movieImgIv = findViewById(R.id.image_iv);
            TextView originalTitleTv = findViewById(R.id.original_title_tv);
            TextView plotSynopsisTv = findViewById(R.id.plot_synopsis_tv);
            TextView userRatingTv = findViewById(R.id.user_rating_tv);
            TextView releaseDateTv = findViewById(R.id.release_date_tv);

            //Get the stored Parcelable movie object
            Movie movie = intent.getParcelableExtra("Movie");

            // Retrieve values from the Movie Object retrieved
            String imgPath = this.getResources().getString(R.string.image_url)+movie.getBackdropPath();
            String originalTitle = movie.getOriginalTitle();
            String plotSynopsis = movie.getOverview();
            Double userRating = movie.getVoteAverage();
            String releaseDate = movie.getReleaseDate();

            // using picasso to load the image
            Picasso.with(this)
                    .load(imgPath)
                    //added a place holder to show the default app icon if no image is returned
                    .placeholder(R.mipmap.ic_launcher)
                    .into(movieImgIv);

            // Setting the text views to the values
            originalTitleTv.setText(originalTitle);
            plotSynopsisTv.setText(plotSynopsis);
            userRatingTv.setText(userRating.toString());
            releaseDateTv.setText(releaseDate);
            setTitle(originalTitle);
        }
    }
    // Respond to error
    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }
}
