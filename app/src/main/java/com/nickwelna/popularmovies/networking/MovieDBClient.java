package com.nickwelna.popularmovies.networking;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieDBClient {

    @GET("/3/movie/popular")
    Call<MovieList> popularMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/top_rated")
    Call<MovieList> highestRatedMovies(@Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int movieID, @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/videos")
    Call<VideoList> getVideos(@Path("movie_id") int movieID, @Query("api_key") String apiKey);

    @GET("/3/movie/{movie_id}/reviews")
    Call<ReviewList> getReviews(@Path("movie_id") int movieID, @Query("api_key") String apiKey);

}
