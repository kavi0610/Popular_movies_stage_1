package com.example.android.popularmovies_stage1_retrofit;

import com.example.android.popularmovies_stage1_retrofit.Model.JSONMovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Request Interface for Retrofit.
 */

interface MovieRequestInterface {
    //Api call to get highest rated movies
    @GET("movie/top_rated")
    Call<JSONMovieResponse> getTopRatedMovies (@Query("api_key") String apiKey);
    //Api call to get popular movies
    @GET("movie/popular")
    Call<JSONMovieResponse> getPopularMovies (@Query("api_key") String apiKey);
}
