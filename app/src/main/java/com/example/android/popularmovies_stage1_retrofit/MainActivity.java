package com.example.android.popularmovies_stage1_retrofit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmovies_stage1_retrofit.Model.JSONMovieResponse;
import com.example.android.popularmovies_stage1_retrofit.Model.Movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView = null;

    private CustomMovieAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recycler view set up
        recyclerView = findViewById(R.id.movie_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);

        //Load the movie poster based on settings
        setupSharedPreferences();


    }

    // Load data based on preferences
    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        //Setting the default option to highest/top rated
        String defaultOption = sharedPreferences.getString("sorting_option","top_rated");
        loadMovieData(defaultOption);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menu inflater for settings
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_settings){
            //Create new intent for settings
            Intent startSettingsActivity = new Intent(this,SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Load data into the view using retrofit
    private void loadMovieData(String defaultOption) {

            final String api_key = getResources().getString(R.string.api_key);
            final String base_url = getResources().getString(R.string.base_url);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieRequestInterface movieRequestInterface = retrofit.create(MovieRequestInterface.class);

        // Sort by top rated
        if (defaultOption.equals("top_rated")) {
            Call<JSONMovieResponse> call = movieRequestInterface.getTopRatedMovies(api_key);

            call.enqueue(new Callback<JSONMovieResponse>() {

                @Override
                public void onResponse(Call<JSONMovieResponse> call, Response<JSONMovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    mAdapter = new CustomMovieAdapter(getApplicationContext(), movies);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<JSONMovieResponse> call, Throwable t) {

                }
            });


        }
        // Sort by popular
        else if (defaultOption.equals("popular")){

            Call<JSONMovieResponse> call = movieRequestInterface.getPopularMovies(api_key);

            call.enqueue(new Callback<JSONMovieResponse>() {

                @Override
                public void onResponse(Call<JSONMovieResponse> call, Response<JSONMovieResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    mAdapter = new CustomMovieAdapter(getApplicationContext(), movies);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<JSONMovieResponse> call, Throwable t) {
                    Log.d("Error on Json call", "Json call: " +t);
                }
            });

        }

    }

    //Handling the  sort order from settings
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            loadFromPreferences(sharedPreferences);
    }

    private void loadFromPreferences(SharedPreferences sharedPreferences) {
        //Get the changed sort order option and load data based on it
        String defaultOption = sharedPreferences.getString(getString(R.string.sort_key_option),getString(R.string.sort_key_top));
        loadMovieData(defaultOption);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister Activity as an OnPreferenceChangedListener to avoid  memory leaks
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

}
